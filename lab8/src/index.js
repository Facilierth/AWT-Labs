const express = require('express');
const WebSocket = require('ws');
const http = require('http');
const path = require('path');
const { v4: uuidv4 } = require('uuid');
const { timeStamp } = require('console');

const app = express();
const server = http.createServer(app);
const wsServer = new WebSocket.Server({ server });

// serve html file
app.use(express.static(path.join(__dirname)));


const clients = new Map();
const rooms = new Map();

// webSocket handling
wsServer.on('connection', ws => {

  const userId = uuidv4();
  const startingUsername = `Guest_${userId.substring(0,10)}`;
  console.log(startingUsername + ' connected');
  clients.set(ws, { id: userId, name: startingUsername, room: null });

  ws.send(JSON.stringify({ type: 'connected', content: startingUsername, id: userId }));

  ws.on('message', message => {
    const data = JSON.parse(message);
    const client = clients.get(ws);
    switch(String(data.type)) {

      case 'setName':
        // if the client is in room check if there is another client with name
        // that client is trying to change into
        if (client.room !== null) {
          for (const [_, iClient] of clients.entries()) {
            if (iClient.name === data.content && iClient.room === client.room) {
              ws.send(JSON.stringify({ type: 'nameSetError', content: "In the room you're in there is already a client with this name." }));
              return;
            }
          }
        }

        const oldName = client.name;
        client.name = data.content;
        ws.send(JSON.stringify({ type: 'nameSet', content: client.name }));
        broadcast(client.room, {
          type: 'info',
          content: `${oldName} has changed the name to ${client.name}.`,
        });
        break;

      case 'joinRoom':

        // check if there already is any client with the name of client that is trying to join
        for (const [_, iClient] of clients.entries()) {
          if (iClient.name === client.name && iClient.room === data.content) {
            ws.send(JSON.stringify({ type: 'nameSetError', content: "In the room you're trying to join there is already a client with this name." }));
            return;
          }
        } 

        const room = data.content;
        if(client.room === room)
          return;
        // remove client from current room
        if (client.room) {
          rooms.get(client.room).delete(ws)
          broadcast(client.room, {
            type: 'info',
            content: `${client.name} has left the room.`,
          });
        }
        client.room = room;
        // if room doesnt exist create new one
        if (!rooms.has(room)) {
          rooms.set(room, new Set());
        }


        rooms.get()
        rooms.get(room).add(ws);
        ws.send(JSON.stringify({ type: 'joinedRoom', content: room }));
        broadcast(room, {
          type: 'info',
          content: `${client.name} has joined the room.`,
        });
        
        break;

      
      case 'image':
      case 'message':
        if (!client.room) 
          return;
        broadcast(client.room, {
          type: String(data.type),
          from: client.name,
          timestamp: getFormattedTimestamp(),
          content: data.content,
          id: client.id
        });
        break;

      case 'typing':
        if (!client.room) 
          return;

        broadcast(client.room, {
          type: 'typing',
          from: client.name,
          content: data.content,
          id: client.id
        });

        break;
        
    }  


  });

  ws.on('close', () => {
    const client = clients.get(ws);
    console.log("Client disconnected")
    if (client?.room) {
      const room = rooms.get(client.room);
      if (room) {
        room.delete(ws);
        broadcast(client.room, {
          type: 'info',
          content: `${client.name} has left the room.`,
        });
      }
    }
    clients.delete(ws);
  });

});

function broadcast(roomName, data) {
  const room = rooms.get(roomName);
  if (!room)
    return;
  const msg = JSON.stringify(data);
  room.forEach(ws => {
    if (ws.readyState === WebSocket.OPEN) {
      ws.send(msg);
    }
  });
}

function getFormattedTimestamp() {
  const now = new Date();

  const options = { month: 'long' };
  const month = new Intl.DateTimeFormat('en-US', options).format(now);
  const day = now.getDate();
  const hour = now.getHours().toString().padStart(2, '0');
  const minute = now.getMinutes().toString().padStart(2, '0');

  return `${month} ${day}, ${hour}:${minute}`;
}


const PORT = 8080;
server.listen(PORT, () => {
  console.log(`HTTP server running at http://localhost:${PORT}`);
});
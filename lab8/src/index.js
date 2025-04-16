const express = require('express');
const WebSocket = require('ws');
const http = require('http');
const path = require('path');

const app = express();
const server = http.createServer(app);
const wss = new WebSocket.Server({ server });

// Serve static files (like index.html)
app.use(express.static(path.join(__dirname)));

// WebSocket handling
wss.on('connection', ws => {
  console.log('Client connected');

  ws.on('message', message => {
    console.log(`Received: ${message}`);
    ws.send(`Echo: ${message}`);
  });

  ws.on('close', () => {
    console.log('Client disconnected');
  });
});

// Start the server
const PORT = 3000;
server.listen(PORT, () => {
  console.log(`HTTP server running at http://localhost:${PORT}`);
});
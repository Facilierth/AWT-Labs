import { createServer } from 'node:http'
import { createSchema, createYoga } from 'graphql-yoga'
import { readFileSync } from 'node:fs'
import { join } from 'node:path'
import axios from 'axios'

const typeDefs = readFileSync(join('src', 'schema.graphql'), 'utf-8')

const usersList = [];
const todosList = [];

async function userById(parent, args, context, info){
    const users = await getRestUsersList();
    return users.find(u => u.id == args.id);
}

async function getRestUsersList(){
    try {
        const users = await axios.get("https://jsonplaceholder.typicode.com/users");
        console.log(users);
        return users.data.map(({ id, name, email, username }) => ({
            id: id,
            name: name,
            email: email,
            login: username,
        }));
    } catch (error) {
        throw error
    }
}

async function getRestTodosList(){
    try {
        // pls work
        const todos = await axios.get("https://jsonplaceholder.typicode.com/todos");
        return todos.data.map(({ id, title, completed, userId }) => ({
            id,
            title,
            completed,
            user_id: userId
        }));
    } catch (error) {
        throw error;
    }
}

async function todoById(parent, args) {
    const todos = await getRestTodosList();
    return todos.find(t => t.id == args.id);
}

const resolvers = {
    Query: {
        users: async () => getRestUsersList(),
        todos: async () => getRestTodosList(),
        todo: (parent, args) => todoById(parent, args),
        user: (parent, args) => userById(parent, args),
    },
    User: {
        todos: async (parent) => {
            const todos = await getRestTodosList();
            return todos.filter(t => t.user_id == parent.id);
        }
    },
    ToDoItem: {
        user: async (parent) => {
            const users = await getRestUsersList();
            return users.find(u => u.id == parent.user_id);
        }
    }
}

const yoga = createYoga({
    schema: createSchema({
        typeDefs: typeDefs,
        resolvers: resolvers
    })
})

const server = createServer(yoga)

server.listen(4000, () => {
    console.info('Server is running on http://localhost:4000/graphql')
})



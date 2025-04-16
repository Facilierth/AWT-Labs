import { createServer } from 'node:http'
import { createSchema, createYoga } from 'graphql-yoga'
import { readFileSync } from 'node:fs'
import { join } from 'node:path'
import axios from 'axios'

const typeDefs = readFileSync(join('src', 'schema.graphql'), 'utf-8')

import {
    getAllUsers,
    getUserById,
    addUser,
    updateUser,
    deleteUser,
    getAllTodos,
    getTodoById,
    addTodo,
    updateTodo,
    deleteTodo,
    getTodosByUserId,
    getUserByTodo
} from './mutation_functions.js'

export const resolvers = {
    Query: {
        users: () => getAllUsers(),
        user: (_, { id }) => getUserById(id),
        todos: () => getAllTodos(),
        todo: (_, { id }) => getTodoById(id),
    },
    Mutation: {
        addUser: (_, args) => addUser(args.name, args.email, args.login),
        updateUser: (_, { id, ...data }) => updateUser(id, data),
        deleteUser: (_, { id }) => deleteUser(id),

        addTodo: (_, args) => addTodo(args.title, args.completed, args.userId),
        updateTodo: (_, { id, ...data }) => updateTodo(id, data),
        deleteTodo: (_, { id }) => deleteTodo(id),
    },
    User: {
        todos: (parent) => getTodosByUserId(parent.id),
    },
    ToDoItem: {
        user: (parent) => getUserByTodo(parent),
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



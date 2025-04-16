import Database from 'better-sqlite3'

const db = new Database('data.db')

export function getAllUsers() {
    return db.prepare('SELECT * FROM users').all()
}

export function getUserById(id) {
    return db.prepare('SELECT * FROM users WHERE id = ?').get(id)
}

export function addUser(name, email, login) {
    const result = db.prepare('INSERT INTO users (name, email, login) VALUES (?, ?, ?)').run(name, email, login)
    return getUserById(result.lastInsertRowid)
}

export function updateUser(id, { name, email, login }) {
    const existing = getUserById(id)
    if (!existing) return null
    db.prepare('UPDATE users SET name = ?, email = ?, login = ? WHERE id = ?')
        .run(name ?? existing.name, email ?? existing.email, login ?? existing.login, id)
    return getUserById(id)
}

export function deleteUser(id) {
    const result = db.prepare('DELETE FROM users WHERE id = ?').run(id)
    return result.changes > 0
}

export function getAllTodos() {
    return db.prepare('SELECT * FROM todos').all()
}

export function getTodoById(id) {
    return db.prepare('SELECT * FROM todos WHERE id = ?').get(id)
}

export function addTodo(title, completed, userId) {
    const result = db.prepare('INSERT INTO todos (title, completed, user_id) VALUES (?, ?, ?)')
        .run(title, completed ? 1 : 0, userId ?? null)
    return getTodoById(result.lastInsertRowid)
}

export function updateTodo(id, { title, completed, userId }) {
    const existing = getTodoById(id)
    if (!existing) return null
    db.prepare('UPDATE todos SET title = ?, completed = ?, user_id = ? WHERE id = ?')
        .run(title ?? existing.title, completed ?? existing.completed, userId ?? existing.user_id, id)
    return getTodoById(id)
}

export function deleteTodo(id) {
    const result = db.prepare('DELETE FROM todos WHERE id = ?').run(id)
    return result.changes > 0
}

export function getTodosByUserId(userId) {
    return db.prepare('SELECT * FROM todos WHERE user_id = ?').all(userId)
}

export function getUserByTodo(todo) {
    return db.prepare('SELECT * FROM users WHERE id = ?').get(todo.user_id)
}

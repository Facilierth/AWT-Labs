import Database from 'better-sqlite3';

const db = new Database('data.db');

db.exec(`
  CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    email TEXT NOT NULL,
    login TEXT NOT NULL
  );

  CREATE TABLE IF NOT EXISTS todos (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    completed BOOLEAN NOT NULL,
    user_id INTEGER,
    FOREIGN KEY (user_id) REFERENCES users(id)
  );
`);

console.log('database initialized');

const users = [
  { name: 'Alice', email: 'alice@a.com', login: 'alice123' },
  { name: 'Kate', email: 'kate@b.com', login: 'katamaran' },
  { name: 'Kacper', email: 'kacper@c.com', login: 'XxkacperxX' }
]

const insertUser = db.prepare('INSERT INTO users (name, email, login) VALUES (?, ?, ?)')
users.forEach(user => insertUser.run(user.name, user.email, user.login))

// Seed todos
const todos = [
  { title: 'Buy milk', completed: 0, user_id: 1 },
  { title: 'Feed the cat', completed: 1, user_id: 1 },
  { title: 'Write Potop', completed: 0, user_id: 2 },
  { title: 'Water plants', completed: 1, user_id: 3 }
]

const insertTodo = db.prepare('INSERT INTO todos (title, completed, user_id) VALUES (?, ?, ?)')
todos.forEach(todo => insertTodo.run(todo.title, todo.completed, todo.user_id))

console.log('Database seeded! (is thath how its written?)')
<template>
    <div id="books-table">
        <table>
            <thead>
                <tr>
                    <th>Id</th>
                    <th>Title</th>
                    <th>Author</th>
                    <th>Pages</th>
                    <th>Available</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <tr v-for="book in booksSource" :key="book.id">
                    <td>{{book.id}}</td>
                    <td>{{book.title}}</td>
                    <td>{{book.author.name}}</td>
                    <td>{{book.pages}}</td>
                    <td>
                        <span v-if="book.lentOut" style="color: red;">❌</span>
                        <span v-else style="color: green;">✅</span>
                    </td>
                    <td>
                        <button @click="$emit('edit:book', book.id)" class="update">Edit</button>
                        <button @click="$emit('delete:book', book.id)" class="delete">Delete</button>
                    </td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<script>
    export default {
        name: 'books-table',
        props: {
            booksSource: Array,
        },
    }
</script>
    <style>
    table {
        width: 100%;
        border-collapse: collapse;
        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
        font-size: 16px;
        margin: 20px 0;
        box-shadow: 0 4px 8px rgba(0,0,0,0.05);
        border-radius: 10px;
        overflow: hidden;
    }

    thead {
        background-color: #f4f4f4;
        color: #333;
    }

    thead {
        padding: 15px;
        text-align: left;
        font-weight: 600;
        border-bottom: 2px solid #ddd;
    }

    th {
        text-align: center;
    }

    tbody tr:nth-child(even) {
        background-color: #fafafa;
    }

    tbody tr:hover {
        background-color: #f1f1f1;
    }

    td {
        padding: 14px;
        border-bottom: 1px solid #eee;
        color: #555;
    }

    button {
        color: azure;
        border-radius: 20%;
        margin-inline: 10px;
        padding: 4px;
    }

    .update {
        background-color: darkblue;
    }

    .delete {
        background-color: darkred;
    }
</style>
<template>
  <div id="author-table">
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Name</th>
        <th>Nationality</th>
        <th>Actions</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="author in authorsSource" :key="author.id">
        <td>{{author.id}}</td>
        <td>{{author.name}}</td>
        <td>{{author.nationality}}</td>
        <td>
          <button class="update">Edit</button>
          <button @click="$emit('delete:author', author.id)" class="delete">Delete</button>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>

<script>
export default {
  name: 'author-table',
  props: {
    authorsSource: Array,
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
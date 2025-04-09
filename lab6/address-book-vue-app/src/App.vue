<template>
    <div v-if="message" :class="['alert', messageType]" >
      {{ message }}
    </div>
  <h2>Books</h2>
  <book-form @addOrEdit:book="addOrUpdateBook"/>
  <books-table :books-source="books" @delete:book="deleteBook" />
</template>

<script>
import BooksTable from './components/book/BooksTable.vue';
import BookForm from './components/book/BookForm.vue';
import axios from 'axios';

export default {
  name: 'App',
  components: {
    BooksTable,
    BookForm
  },
  data() {
    return {
      books: [],
      message: '',
      messageType: ''
    }
  },
  methods: {
    async getBooks() {
      axios.get('http://localhost:8080/books')
      .then(response => {
        console.log(response.data[0]);
        this.books = response.data;
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    },

    async deleteBook(id) {
      axios.delete(`http://localhost:8080/books/${id}`)
      .then(response => {
        console.log(response);

        this.alert(`Book with id:${id} was deleted.`, 'danger')

        this.getBooks();
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
    },

    addOrUpdateBook(bookForm, editingBookId) {
      if (editingBookId !== null) {
        // Update book
        axios.patch(`http://localhost:8080/books/${editingBookId}`, bookForm)
          .then(() => {
            this.getBooks(); // Refresh books list
            this.alert(`Book with id:${editingBookId} was updated.`, 'success')
          })
          .catch((error) => {
            console.error('Error updating book:', error);
            this.alert(`Book with id:${editingBookId} failed to be updated.`, 'danger')
          });
      } else {
        // Add new book
        axios.post('http://localhost:8080/books', bookForm)
          .then(() => {
            this.getBooks(); // Refresh books list
            this.alert(`New book was created.`, 'success')
          })
          .catch((error) => {
            console.error('Error adding book:', error);
            this.alert(`Book failed to be created.`, 'danger')
          });
      }
    },

    async alert(message, messageType) {
      this.message = message;
      this.messageType = messageType;

      setTimeout(() => {
        this.message = '';
        this.messageType = '';
      }, 2800);
    }
  },
  mounted() {
    this.getBooks();
  }
}
</script>

<style>
  #app {
    font-family: Avenir, Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    color: #2c3e50;
    margin-top: 60px;
  }

  .alert {
    position: fixed;
    top: 10px;
    left: 50%;
    transform: translateX(-50%);
    padding: 10px 20px;
    border-radius: 8px;
    font-size: 16px;
    font-weight: bold;
    z-index: 9999;
    transition: opacity 0.3s ease-in-out;
    opacity: 1;
    width: 30%; 
    max-width: 500px;
  }

  .danger {
    background-color: #dc3545;
    color: white;
    border-left: 5px solid #c82333;
  }

  .success {
    background-color: green;
    color: white;
    border-left: 5px solid greenyellow;
  }

</style>

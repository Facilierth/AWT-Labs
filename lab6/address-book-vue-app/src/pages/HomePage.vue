<template>
  <MainLayout>
    <div v-if="message" :class="['alert', messageType]">{{ message }}</div>
    <h2>Books</h2>
    <book-form :book-to-edit="bookToEdit" @addOrEdit:book="addOrUpdateBook" />
    <books-table :books-source="books" @delete:book="deleteBook" @edit:book="editBook" />
  </MainLayout>
</template>

<script>
import MainLayout from '@/layouts/MainLayout.vue'
import BooksTable from '@/components/book/BooksTable.vue'
import BookForm from '@/components/book/BookForm.vue'
import axios from 'axios'

export default {
  name: 'HomePage',
  components: { MainLayout, BooksTable, BookForm },
  data() {
    return {
      books: [],
      bookToEdit: null,
      message: '',
      messageType: ''
    }
  },
  mounted() {
    this.getBooks();
  },
  methods: {
    async getBooks() {
      try {
        const res = await axios.get('http://localhost:8080/books');
        this.books = res.data;
      } catch (err) {
        console.error(err);
      }
    },
    async deleteBook(id) {
      try {
        await axios.delete(`http://localhost:8080/books/${id}`);
        this.alert(`Book with id:${id} was deleted.`, 'danger');
        this.getBooks();
      } catch (err) {
        console.error(err);
      }
    },

    async editBook(book) {
      this.bookToEdit = book;
    },

    addOrUpdateBook(bookForm, editingBookId) {
      const request = editingBookId !== null
          ? axios.patch(`http://localhost:8080/books/${editingBookId}`, bookForm)
          : axios.post('http://localhost:8080/books', bookForm);

      request.then(() => {
        this.getBooks();
        const msg = editingBookId !== null
            ? `Book with id:${editingBookId} was updated.`
            : `New book was created.`;
        this.alert(msg, 'success');
      }).catch(() => {
        const msg = editingBookId !== null
            ? `Book with id:${editingBookId} failed to be updated.`
            : `Book failed to be created.`;
        this.alert(msg, 'danger');
      });
      this.cancelEdit();
    },

    cancelEdit() {
      this.bookToEdit = null;
    },

    alert(message, type) {
      this.message = message;
      this.messageType = type;
      setTimeout(() => {
        this.message = '';
        this.messageType = '';
      }, 2800);
    }
  }
}
</script>

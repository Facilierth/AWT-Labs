<template>
  <MainLayout>
    <div v-if="message" :class="['alert', messageType]">{{ message }}</div>
    <h2>Books</h2>
    <book-form :book-to-edit="bookToEdit" :authors="authors" @addOrEdit:book="addOrUpdateBook" />
    <books-table :books-source="books" @delete:book="deleteBook" @edit:book="editBook" @lend:book="lendBook"/>
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
      authors: [],
      bookToEdit: null,
      message: '',
      messageType: ''
    }
  },
  mounted() {
    this.getBooks();
    this.getAuthors();
    
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

    async getAuthors() {
      try {
        const res = await axios.get('http://localhost:8080/authors');
        this.authors = res.data;
      } catch (err) {
        console.error(err);
      }
    },

    async deleteBook(id) {
      console.log(this.authors);
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

    async lendBook(book) {
      const id = book.id;
      const url = book.lentOut ? `http://localhost:8080/lending/retrieve/${id}` : `http://localhost:8080/lending/${id}`; 
      const successMessage = book.lentOut ? `Book with id:${id} was returned.` : `Book with id:${id} was lent out.`;
      const failureMessage = book.lentOut ? `Book with id:${id} failed to be returned.` : `Book with id:${id} failed to be lent out.`;
      try {
        await axios.patch(url);
        this.alert(successMessage, 'success');
      } catch (err) {
        console.error(err);
        this.alert(failureMessage, 'danger');
      } finally {
        this.getBooks();
      }

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

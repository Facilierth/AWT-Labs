<template>
  <MainLayout>
    <div v-if="message" :class="['alert', messageType]">{{ message }}</div>
    <h2>Authors</h2>
    <author-form :author-to-edit="authorToEdit" @edit:author="editAuthor" @addOrEdit:author="addOrUpdateAuthor" />
    <author-table :authors-source="authors" @delete:author="deleteAuthor" @edit:author="editAuthor"/>
  </MainLayout>
</template>

<script>
import MainLayout from '@/layouts/MainLayout.vue'
import AuthorTable from "@/components/authors/AuthorTable.vue";
import axios from 'axios'
import AuthorForm from "@/components/authors/AuthorForm.vue";

export default {
  name: 'AuthorPage',
  components: {MainLayout, AuthorTable, AuthorForm },
  data() {
    return {
      authors: [],
      authorToEdit: null,
      message: '',
      messageType: ''
    }
  },
  mounted() {
    this.getAuthors();
  },
  methods: {
    async getAuthors() {
      try {
        const res = await axios.get('http://localhost:8080/authors');
        this.authors = res.data;
      } catch (err) {
        console.error(err);
      }
    },
    async deleteAuthor(id) {
      try {
        await axios.delete(`http://localhost:8080/authors/${id}`);
        this.alert(`Author with id:${id} was deleted.`, 'danger');
        await this.getAuthors();
      } catch (err) {
        console.error(err);
      }
    },

    async editAuthor(author){
      this.authorToEdit = author;
    },

    addOrUpdateAuthor(authorForm, editingAuthorId) {
      const request = editingAuthorId !== null
          ? axios.patch(`http://localhost:8080/authors/${editingAuthorId}`, authorForm)
          : axios.post('http://localhost:8080/authors', authorForm);

      request.then(() => {
        this.getAuthors();
        const msg = editingAuthorId !== null
            ? `Author with id:${editingAuthorId} was updated.`
            : `New Author was created.`;
        this.alert(msg, 'success');
      }).catch(() => {
        const msg = editingAuthorId !== null
            ? `Author with id:${editingAuthorId} failed to be updated.`
            : `Author failed to be created.`;
        this.alert(msg, 'danger');
      });
      this.cancelEdit();
    },
    cancelEdit(){
      this.authorToEdit = null;
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
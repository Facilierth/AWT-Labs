<template>
  <div class="form-container">
    <h3 v-if="editingAuthorId !== null" class="form-header">
      Editing author {{ this.editingAuthorId }}
    </h3>
    <form @submit.prevent="addOrUpdateAuthor" class="author-form">
      <div class="form-group">
        <label for="name">Name:</label>
        <input type="text" id="name" v-model="this.authorForm.name" required class="form-input" />
      </div>

      <div class="form-group">
        <label for="nationality">Nationality:</label>
        <input type="text" id="nationality" v-model="this.authorForm.nationality" required class="form-input" />
      </div>

      <button class="submit-button" type="submit">
        {{ this.editingAuthorId !== null ? 'Update Author' : 'Add Author' }}
      </button>
    </form>
  </div>
</template>


<script>
export default {
  name: 'author-form',
  props: {
    editingAuthorId: Number,
  },
  data() {
    return {
      authorForm: {
        name: '',
        nationality : '',
      },
    }
  },
  methods: {
    addOrUpdateAuthor() {
      this.message = '';
      this.$emit('addOrEdit:author', this.authorForm, this.editingAuthorId);

      this.authorForm.name = '';
      this.authorForm.nationality = '';
    },
  },
}
</script>

<style scoped>
.form-container {
  max-width: 400px;
  margin: 20px auto;
  padding: 20px;
  background-color: #f9f9f9;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

.form-header {
  text-align: center;
  font-size: 1.5rem;
  color: #333;
  margin-bottom: 20px;
  font-weight: 600;
}

/* Form styling */
.author-form {
  display: flex;
  flex-direction: column;
}

.form-group {
  margin-bottom: 15px;
}

.form-group label {
  font-size: 1rem;
  font-weight: 500;
  color: #555;
  margin-bottom: 5px;
  display: block;
}

.form-input {
  width: 100%;
  padding: 10px;
  font-size: 1rem;
  border: 1px solid #ddd;
  border-radius: 4px;
  transition: border-color 0.3s ease;
}

.form-input:focus {
  border-color: #4CAF50;
  outline: none;
}

.submit-button {
  background-color: #4CAF50;
  color: white;
  padding: 12px 20px;
  font-size: 1.1rem;
  font-weight: 600;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: background-color 0.3s ease;
  margin-top: 10px;
}

.submit-button:hover {
  background-color: #45a049;
}

.submit-button:active {
  background-color: #388e3c;
}

.submit-button:disabled {
  background-color: #a5d6a7;
  cursor: not-allowed;
}
</style>
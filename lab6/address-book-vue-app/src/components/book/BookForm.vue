<template>
    <div class="form-container">
        <h3 v-if="bookToEdit !== null" class="form-header">
            Editing book {{ this.bookToEdit.id }}
        </h3>    
        <form @submit.prevent="addOrUpdateBook" class="book-form">
            <div class="form-group">
                <label for="title">Title:</label>
                <input type="text" id="title" v-model="this.bookForm.title" required class="form-input" />
            </div>
    
            <div class="form-group">
                <label for="authorId">Author Id:</label>
                <input type="number" id="authorId" v-model="this.bookForm.authorId" required class="form-input" />
            </div>
    
            <div class="form-group">
                <label for="pages">Pages:</label>
                <input type="number" id="pages" v-model="this.bookForm.pages" required class="form-input" />
            </div>
    
            <button class="submit-button" type="submit">
                {{ this.bookToEdit !== null ? 'Update Book' : 'Add Book' }}
            </button>
        </form>
    </div>
</template>


<script>
    export default {
        name: 'book-form',
        props: {
            bookToEdit: Object,
        },
        data() {
            return {
                bookForm: {
                    title: '',
                    pages: null,
                    authorId: null,
                },
            }
        },
        methods: {
            addOrUpdateBook() {
                this.message = '';
                this.$emit('addOrEdit:book', this.bookForm, this.bookToEdit !== null ? this.bookToEdit.id : null);

                this.bookForm.title = '';
                this.bookForm.authorId = null;
                this.bookForm.pages = null;
            },
        },
        watch: {
            bookToEdit: {
                immediate: true,
                handler(newVal) {
                    if (newVal) {
                        this.bookForm = {
                            title: newVal.title,
                            pages: newVal.pages,
                            authorId: newVal.author.id, // assuming it's an object
                        };
                    }
                }
            }
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
    .book-form {
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
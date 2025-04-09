<template>
  <div>
    <header class="header">
      <h1>📚 Book Manager</h1>
      <nav>
        <router-link to="/">Home</router-link>
        <router-link to="/authors">Authors</router-link>
      </nav>
    </header>
    <main class="main">
      <slot />
    </main>
    <FooterLayout>

    </FooterLayout>
  </div>
</template>

<style>
.header {
  background-color: #4CAF50;
  color: white;
  padding: 1rem;
  text-align: center;
}

.main {
  padding: 2rem;
}
 #app {
   font-family: Avenir, Helvetica, Arial, sans-serif;
   -webkit-font-smoothing: antialiased;
   -moz-osx-font-smoothing: grayscale;
   text-align: center;
   color: #2c3e50;
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

nav {
  display: flex;
  justify-content: center;
  gap: 2rem;
}

nav a {
  color: #bbb;
  text-decoration: none;
  font-weight: 600;
  font-size: 1.1rem;
  transition: color 0.3s;
}

nav a:hover,
nav a.active-link {
  color: #fff;
  border-bottom: 2px solid #fff;
}

</style>
<script setup>
import FooterLayout from "@/layouts/FooterLayout.vue";
</script>
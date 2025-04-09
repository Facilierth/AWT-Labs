<template>
  <div>
    <header class="header">
      <h1>📚 Book Manager</h1>
    </header>
    <main class="main">
      <slot />
    </main>
  </div>
</template>

<style scoped>
.header {
  background-color: #4CAF50;
  color: white;
  padding: 1rem;
  text-align: center;
}

.main {
  padding: 2rem;
}
</style>
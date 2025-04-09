<template>
  <MainLayout>
    <h2>404 - Page Not Found</h2>
    <p>The page you're looking for doesn't exist.</p>
  </MainLayout>
</template>

<script>

import MainLayout from '@/layouts/MainLayout.vue'

export default {
  components: { MainLayout }
}
</script>

<style scoped>
  h2, p {
    text-align: center;
    font-family: sans-serif;
    font-size: larger;
  }

  p {
    font-size: medium;
  }
</style>

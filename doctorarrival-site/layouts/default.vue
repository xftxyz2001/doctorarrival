<template>
  <div class="body">
    <PageHeader />
    <main class="container">
      <slot />
    </main>
    <PageFooter />
  </div>
</template>

<style>
.body {
  @apply bg-gray-100 flex flex-col min-h-screen min-w-1000px;
}
</style>
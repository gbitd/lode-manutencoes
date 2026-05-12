<script setup>
import { ref, onMounted } from 'vue'
import { getHello } from '@/services/helloService'

const mensagem = ref('')
const carregando = ref(true)

onMounted(async () => {
  mensagem.value = await getHello()
  carregando.value = false
})
</script>

<template>
  <div>
    <p v-if="carregando">Carregando...</p>
    <p v-else>{{ mensagem }}</p>
  </div>
</template>

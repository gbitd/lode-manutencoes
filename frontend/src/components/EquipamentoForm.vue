<script setup>
import { ref, watch } from 'vue'

const props = defineProps({
  equipamentoEditando: { type: Object, default: null }
})

const emit = defineEmits(['salvar', 'cancelar'])

const form = ref({
  nome: '',
  tipo: '',
  dataInstalacao: '',
  status: 'OPERACIONAL'
})

// Preenche o form quando estiver editando
watch(() => props.equipamentoEditando, (eq) => {
  if (eq) form.value = { ...eq }
  else form.value = { nome: '', tipo: '', dataInstalacao: '', status: 'OPERACIONAL' }
}, { immediate: true })

function submeter() {
  emit('salvar', { ...form.value })
}
</script>

<template>
  <div class="form-container">
    <h2>{{ equipamentoEditando ? 'Editar' : 'Novo' }} Equipamento</h2>

    <div class="field">
      <label>Nome</label>
      <input v-model="form.nome" placeholder="Ex: Bomba 01" />
    </div>

    <div class="field">
      <label>Tipo</label>
      <input v-model="form.tipo" placeholder="Ex: Bomba, Painel, Tubulação" />
    </div>

    <div class="field">
      <label>Data de Instalação</label>
      <input v-model="form.dataInstalacao" type="date" />
    </div>

    <div class="field">
      <label>Status</label>
      <select v-model="form.status">
        <option value="OPERACIONAL">Operacional</option>
        <option value="MANUTENCAO_NECESSARIA">Manutenção Necessária</option>
        <option value="CRITICO">Crítico</option>
      </select>
    </div>

    <div class="actions">
      <button @click="submeter">Salvar</button>
      <button @click="emit('cancelar')">Cancelar</button>
    </div>
  </div>
</template>

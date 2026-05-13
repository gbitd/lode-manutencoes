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

watch(() => props.equipamentoEditando, (eq) => {
  form.value = eq
    ? { ...eq }
    : { nome: '', tipo: '', dataInstalacao: '', status: 'OPERACIONAL' }
}, { immediate: true })

function submeter() {
  emit('salvar', { ...form.value })
}
</script>

<template>
  <div class="form-card">
    <h2>{{ equipamentoEditando ? 'Editar equipamento' : 'Novo equipamento' }}</h2>

    <div class="form-fields">
      <div class="field">
        <label>Nome</label>
        <input v-model="form.nome" placeholder="Ex: Bomba 01" />
      </div>

      <div class="field">
        <label>Tipo</label>
        <input v-model="form.tipo" placeholder="Ex: Bomba, Painel, Tubulação" />
      </div>

      <div class="field">
        <label>Data de instalação</label>
        <input v-model="form.dataInstalacao" type="date" />
      </div>

      <div class="field">
        <label>Status</label>
        <select v-model="form.status">
          <option value="OPERACIONAL">Operacional</option>
          <option value="MANUTENCAO_NECESSARIA">Manutenção necessária</option>
          <option value="CRITICO">Crítico</option>
        </select>
      </div>
    </div>

    <div class="form-actions">
      <button class="btn" @click="emit('cancelar')">Cancelar</button>
      <button class="btn btn-primary" @click="submeter">Salvar</button>
    </div>
  </div>
</template>

<style scoped>
.form-card {
  background: #fff;
  border: 0.5px solid #e8e8e8;
  border-radius: 12px;
  padding: 1.25rem 1.5rem;
  margin-bottom: 1.75rem;
}

.form-card h2 {
  margin-bottom: 1.25rem;
  color: #1a1a1a;
}

.form-fields {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 12px;
  margin-bottom: 1.25rem;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: 8px;
}
</style>

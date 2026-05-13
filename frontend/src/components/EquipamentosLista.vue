<script setup>
import { ref, computed, onMounted } from 'vue'
import {
  listarEquipamentos,
  deletarEquipamento,
  atualizarStatus,
  criarEquipamento,
  atualizarEquipamento
} from '@/services/equipamentosService'
import EquipamentoForm from './EquipamentoForm.vue'

const equipamentos = ref([])
const equipamentoEditando = ref(null)
const mostrarForm = ref(false)
const erro = ref('')
const filtroNome = ref('')

const equipamentosFiltrados = computed(() => {
  const termo = filtroNome.value.toLowerCase()
  return equipamentos.value.filter(eq =>
    eq.nome.toLowerCase().includes(termo)
  )
})

const statusClasse = {
  OPERACIONAL: 'badge-operacional',
  MANUTENCAO_NECESSARIA: 'badge-manutencao',
  CRITICO: 'badge-critico'
}

async function carregar() {
  try {
    equipamentos.value = await listarEquipamentos()
  } catch (e) {
    erro.value = e.message
  }
}

async function salvar(dados) {
  try {
    if (equipamentoEditando.value) {
      await atualizarEquipamento(equipamentoEditando.value.id, dados)
    } else {
      await criarEquipamento(dados)
    }
    mostrarForm.value = false
    equipamentoEditando.value = null
    await carregar()
  } catch (e) {
    erro.value = e.message
  }
}

async function alterarStatus(id, status) {
  try {
    await atualizarStatus(id, status)
    const eq = equipamentos.value.find(e => e.id === id)
    if (eq) eq.status = status
  } catch (e) {
    erro.value = e.message
  }
}

async function remover(id) {
  if (!confirm('Deseja remover este equipamento?')) return
  try {
    await deletarEquipamento(id)
    await carregar()
  } catch (e) {
    erro.value = e.message
  }
}

function editar(equipamento) {
  equipamentoEditando.value = equipamento
  mostrarForm.value = true
}

function novoEquipamento() {
  equipamentoEditando.value = null
  mostrarForm.value = true
}

onMounted(carregar)
</script>

<template>
  <div class="lista-page">

    <div class="lista-header">
      <h1>Equipamentos</h1>
      <button class="btn btn-primary" @click="novoEquipamento">+ Novo equipamento</button>
    </div>

    <p v-if="erro" class="msg-erro">{{ erro }}</p>

    <EquipamentoForm
      v-if="mostrarForm"
      :equipamentoEditando="equipamentoEditando"
      @salvar="salvar"
      @cancelar="mostrarForm = false"
    />

    <div class="search-wrap">
      <input
        v-model="filtroNome"
        type="text"
        placeholder="Buscar por nome..."
      />
    </div>

    <table v-if="equipamentosFiltrados.length">
      <thead>
        <tr>
          <th>Nome</th>
          <th>Tipo</th>
          <th>Instalação</th>
          <th>Status</th>
          <th>Ações</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="eq in equipamentosFiltrados" :key="eq.id">
          <td>{{ eq.nome }}</td>
          <td class="td-muted">{{ eq.tipo }}</td>
          <td class="td-muted">{{ eq.dataInstalacao }}</td>
          <td>
            <select
              class="badge"
              :class="statusClasse[eq.status]"
              :value="eq.status"
              @change="alterarStatus(eq.id, $event.target.value)"
            >
              <option value="OPERACIONAL">Operacional</option>
              <option value="MANUTENCAO_NECESSARIA">Manutenção necessária</option>
              <option value="CRITICO">Crítico</option>
            </select>
          </td>
          <td>
            <div class="acoes">
              <button class="btn btn-sm" @click="editar(eq)">Editar</button>
              <button class="btn btn-sm btn-danger" @click="remover(eq.id)">Remover</button>
            </div>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="!mostrarForm" class="msg-vazio">
      {{ filtroNome ? 'Nenhum equipamento encontrado.' : 'Nenhum equipamento cadastrado.' }}
    </p>

  </div>
</template>

<style scoped>
.lista-page {
  max-width: 900px;
  margin: 0 auto;
  padding: 2.5rem 2rem;
}

.lista-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 2rem;
}

.search-wrap {
  margin-bottom: 1rem;
}

.search-wrap input {
  max-width: 320px;
}

.td-muted {
  color: #888;
}

.acoes {
  display: flex;
  gap: 6px;
}
</style>

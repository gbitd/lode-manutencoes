<script setup>
import { ref, onMounted } from 'vue'
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
    await carregar()
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

const statusLabel = {
  OPERACIONAL: 'Operacional',
  MANUTENCAO_NECESSARIA: 'Manutenção Necessária',
  CRITICO: 'Crítico'
}

onMounted(carregar)
</script>

<template>
  <div>
    <div class="header">
      <h1>Equipamentos</h1>
      <button @click="novoEquipamento">+ Novo Equipamento</button>
    </div>

    <p v-if="erro" class="erro">{{ erro }}</p>

    <EquipamentoForm
      v-if="mostrarForm"
      :equipamentoEditando="equipamentoEditando"
      @salvar="salvar"
      @cancelar="mostrarForm = false"
    />

    <table v-if="equipamentos.length">
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
        <tr v-for="eq in equipamentos" :key="eq.id">
          <td>{{ eq.nome }}</td>
          <td>{{ eq.tipo }}</td>
          <td>{{ eq.dataInstalacao }}</td>
          <td>
              <select
                :value="eq.status"
                @change="alterarStatus(eq.id, $event.target.value)"
            >
                <option value="" disabled>Alterar status</option>
                <option value="OPERACIONAL">Operacional</option>
                <option value="MANUTENCAO_NECESSARIA">Manutenção Necessária</option>
                <option value="CRITICO">Crítico</option>
            </select>
          </td>
          <td class="acoes">
            <button @click="editar(eq)">Editar</button>
            <button @click="remover(eq.id)">Remover</button>
          </td>
        </tr>
      </tbody>
    </table>

    <p v-else-if="!mostrarForm">Nenhum equipamento cadastrado.</p>
  </div>
</template>

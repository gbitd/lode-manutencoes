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

const statusClasse = {
  OPERACIONAL: 'badge-operacional',
  MANUTENCAO_NECESSARIA: 'badge-manutencao',
  CRITICO: 'badge-critico'
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

    <div class="filtro-wrap">
      <span class="filtro-icon">🔍</span>
      <input
        v-model="filtroNome"
        type="text"
        placeholder="Buscar por nome..."
        class="filtro-input"
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
          <td>{{ eq.tipo }}</td>
          <td>{{ eq.dataInstalacao }}</td>
          <td>
              <select
                class="badge"
                :class="statusClasse[eq.status]"
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

<style scoped>
.container { padding: 2rem; }

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1.5rem;
}

h1 { font-size: 1.5rem; font-weight: 600; }

.btn-novo {
  background: #1a1a1a;
  color: white;
  border: none;
  border-radius: 8px;
  padding: 8px 16px;
  cursor: pointer;
  font-size: 14px;
}

.filtro-wrap {
  position: relative;
  margin-bottom: 1.25rem;
}

.filtro-icon {
  position: absolute;
  left: 10px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 14px;
}

.filtro-input {
  width: 100%;
  padding: 8px 12px 8px 32px;
  border: 1px solid #ddd;
  border-radius: 8px;
  font-size: 14px;
}

table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

th {
  text-align: left;
  padding: 10px 12px;
  color: #666;
  font-weight: 500;
  border-bottom: 1px solid #eee;
  font-size: 13px;
}

td {
  padding: 12px;
  border-bottom: 1px solid #f0f0f0;
}

/* Badges de status */
.badge {
  display: inline-block;
  padding: 3px 10px;
  border-radius: 99px;
  font-size: 12px;
  font-weight: 500;
}

.badge-operacional {
  background: #EAF3DE;
  color: #27500A;
}

.badge-manutencao {
  background: #FAEEDA;
  color: #633806;
}

.badge-critico {
  background: #FCEBEB;
  color: #791F1F;
}

/* Botões de ação */
.acoes { display: flex; gap: 6px; }

.btn-action {
  border: 1px solid #ddd;
  background: transparent;
  border-radius: 6px;
  padding: 4px 10px;
  font-size: 12px;
  cursor: pointer;
  color: #555;
}

.btn-manutencao {
  border-color: #EF9F27;
  color: #854F0B;
}

.btn-remover {
  border-color: #F09595;
  color: #A32D2D;
}

.erro { color: #A32D2D; margin-bottom: 1rem; }
.vazio { color: #888; margin-top: 1rem; }
</style>

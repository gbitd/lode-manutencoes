const BASE_URL = '/api/equipamentos'

export async function listarEquipamentos(page = 0, size = 10) {
  const response = await fetch(BASE_URL + `?page=${page}&size=${size}`)
  if (!response.ok) throw new Error('Erro ao listar equipamentos')
  return response.json()
}

export async function criarEquipamento(equipamento) {
  const response = await fetch(BASE_URL, {
    method: 'POST',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(equipamento)
  })
  if (!response.ok) throw new Error('Erro ao criar equipamento')
  return response.json()
}

export async function atualizarEquipamento(id, equipamento) {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: 'PUT',
    headers: { 'Content-Type': 'application/json' },
    body: JSON.stringify(equipamento)
  })
  if (!response.ok) throw new Error('Erro ao atualizar equipamento')
  return response.json()
}

export async function atualizarStatus(id, status) {
  const response = await fetch(`${BASE_URL}/${id}/status?status=${status}`, {
    method: 'PATCH'
  })
  if (!response.ok) throw new Error('Status inválido ou equipamento não encontrado')
  return response.json()
}

export async function deletarEquipamento(id) {
  const response = await fetch(`${BASE_URL}/${id}`, {
    method: 'DELETE'
  })
  if (!response.ok) throw new Error('Erro ao deletar equipamento')
}

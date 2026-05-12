export async function getHello() {
  const response = await fetch('/api/hello')
  return response.text()
}

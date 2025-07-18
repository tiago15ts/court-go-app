

export function generateRandomPassword(length: number = 8): string {
  const lowercase = "abcdefghijklmnopqrstuvwxyz";
  const uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
  const numbers = "0123456789";
  const special = "!@#$%^&*()_+[]{}<>?/";

  const all = lowercase + uppercase + numbers + special;

  // Garantir pelo menos uma de cada categoria
  let password = [
    lowercase[Math.floor(Math.random() * lowercase.length)],
    uppercase[Math.floor(Math.random() * uppercase.length)],
    numbers[Math.floor(Math.random() * numbers.length)],
    special[Math.floor(Math.random() * special.length)],
  ];

  // Preencher o restante
  for (let i = password.length; i < length; i++) {
    password.push(all[Math.floor(Math.random() * all.length)]);
  }

  // Embaralhar o array para que os caracteres obrigatórios não fiquem nas mesmas posições
  password = password.sort(() => Math.random() - 0.5);

  return password.join('');
}

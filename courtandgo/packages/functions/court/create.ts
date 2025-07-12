import { createCourt } from "../../core/queries/court";

export async function handler(event: any) {
  try {
    const body = JSON.parse(event.body || "{}");
    const { name, clubId, type, surfaceType, capacity, pricePerHour } = body;

    // Validação básica
    if (!name || !clubId || !type || capacity == null || pricePerHour == null) {
      return {
        statusCode: 400,
        body: JSON.stringify({ error: "Campos obrigatórios em falta." }),
      };
    }

    if (!["Tennis", "Padel"].includes(type)) {
      return {
        statusCode: 400,
        body: JSON.stringify({ error: "Tipo de court inválido." }),
      };
    }

    const newCourt = await createCourt({
      name,
      clubId,
      type,
      surfaceType,
      capacity,
      pricePerHour,
    });

    return {
      statusCode: 201,
      body: JSON.stringify(newCourt),
    };
  } catch (error) {
    console.error("Erro ao criar court:", error);
    return {
      statusCode: 500,
      body: JSON.stringify({ error: "Erro interno ao criar court." }),
    };
  }
}

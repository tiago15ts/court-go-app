import { emailNotifications } from "../../core/queries/user";

export async function handler(event) {
    const { id } = event.pathParameters || {};

    const body = JSON.parse(event.body || "{}");
    console.log("body", body);
    console.log(body.enabled);

    //if (typeof id !== "number" || typeof body.enabled !== "boolean") {
    //    return { statusCode: 400, body: JSON.stringify({ error: "Parâmetros inválidos." }) };
    //}

    try {
        const success = await emailNotifications(id, body.enabled);
        console.log("success", success);
        return { statusCode: 200, body: JSON.stringify(success) };
    } catch (error) {
        console.error("Erro ao atualizar notificações por email:", error);
        return { statusCode: 500, body: JSON.stringify({ error: "Erro interno do servidor." }) };
    }
}
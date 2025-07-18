import { updateUser } from "../../core/queries/user";

export async function handler(event) {
    const body = JSON.parse(event.body || "{}");
    const { id } = event.pathParameters || {};

    if (!id) {
        return { statusCode: 400, body: JSON.stringify({ error: "ID do utilizador é obrigatório." }) };
    }

    try {
        const updatedUser = await updateUser({
            id: id,
            email: body.email,
            name: body.name,
            countryCode: body.countryCode,
            phone: body.phone,
            gender: body.gender,
            birthdate: body.birthdate,
            weight: body.weight,
            height: body.height,
        });

        return {
            statusCode: 200,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(updatedUser),
        };
    } catch (error) {
        return {
            statusCode: 500,
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ error: "Erro ao atualizar perfil." })
        };
    }
}
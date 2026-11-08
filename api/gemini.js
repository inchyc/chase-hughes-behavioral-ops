import { GoogleGenAI } from "@google/genai";

export default async function handler(req, res) {
  if (req.method !== "POST") {
    return res.status(405).json({ error: "Method Not Allowed" });
  }

  try {
    const { notes, track } = req.body || {};
    if (!notes) {
      return res.status(400).json({ error: "Missing notes payload" });
    }

    const client = new GoogleGenAI({});
    const interaction = await client.interactions.create({
      model: "gemini-3.8-flash",
      system_instruction:
        "You are an expert NCI (Nonverbal Communication Institute) and Chase Hughes behavioral profiling instructor. Convert the user's raw book or course notes into concise 'Concept: Tactical Definition & Field Application' lines suitable for SM-2 flashcards.",
      input: `Track: ${track || "NCI Behavioral Profiling"}\n\nNotes:\n${notes}`,
    });

    return res.status(200).json({
      formattedLines: interaction.output_text || "",
    });
  } catch (error) {
    return res.status(500).json({
      error: error?.message || "Gemini API request failed",
    });
  }
}

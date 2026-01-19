import { z } from 'zod';

const HealthSchema = z.object({ status: z.string() });

export default function Home() {
  // Example Zod usage; replace with real API data later.
  const result = HealthSchema.parse({ status: 'ok' });

  return (
    <main>
      <h1>QuantivyX AI</h1>
      <p>Health: {result.status}</p>
    </main>
  );
}

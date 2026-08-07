export type VocabWord = {
  id: number;
  word: string;
  meaning: string;
  root: string;
  rootMeaning: string;
  example: string;
};

/** 50 words, grouped by Latin/Greek root so they appear in learning order. */
export const VOCAB: VocabWord[] = [
  // spect — look
  {
    id: 1,
    word: "inspect",
    meaning: "to look at something carefully",
    root: "spect",
    rootMeaning: "to look",
    example: "The mechanic will inspect the engine before the trip.",
  },
  {
    id: 2,
    word: "respect",
    meaning: "to admire someone; regard earned by behavior",
    root: "spect",
    rootMeaning: "to look",
    example: "She earned the team's respect through steady work.",
  },
  {
    id: 3,
    word: "prospect",
    meaning: "the chance of something happening; a possible future",
    root: "spect",
    rootMeaning: "to look",
    example: "The prospect of rain cancelled the picnic.",
  },
  {
    id: 4,
    word: "spectacle",
    meaning: "a visually striking display or performance",
    root: "spect",
    rootMeaning: "to look",
    example: "The fireworks made a dazzling spectacle.",
  },
  {
    id: 5,
    word: "spectator",
    meaning: "a person who watches an event",
    root: "spect",
    rootMeaning: "to look",
    example: "Every spectator stood when the final goal was scored.",
  },

  // port — carry
  {
    id: 6,
    word: "transport",
    meaning: "to carry people or goods from one place to another",
    root: "port",
    rootMeaning: "to carry",
    example: "Trucks transport fresh produce across the country.",
  },
  {
    id: 7,
    word: "export",
    meaning: "to send goods to another country for sale",
    root: "port",
    rootMeaning: "to carry",
    example: "The factory exports coffee beans worldwide.",
  },
  {
    id: 8,
    word: "import",
    meaning: "to bring goods into a country from abroad",
    root: "port",
    rootMeaning: "to carry",
    example: "They import olive oil from Spain.",
  },
  {
    id: 9,
    word: "portable",
    meaning: "easy to carry or move",
    root: "port",
    rootMeaning: "to carry",
    example: "She packed a portable charger for the hike.",
  },
  {
    id: 10,
    word: "deport",
    meaning: "to force someone to leave a country",
    root: "port",
    rootMeaning: "to carry",
    example: "The court ordered the authorities to deport him.",
  },

  // dict — say / speak
  {
    id: 11,
    word: "dictate",
    meaning: "to say something aloud for someone to write; to control",
    root: "dict",
    rootMeaning: "to say",
    example: "The manager will dictate the new rules tomorrow.",
  },
  {
    id: 12,
    word: "predict",
    meaning: "to say what will happen in the future",
    root: "dict",
    rootMeaning: "to say",
    example: "Experts predict warmer weather this week.",
  },
  {
    id: 13,
    word: "contradict",
    meaning: "to say the opposite of what someone else said",
    root: "dict",
    rootMeaning: "to say",
    example: "Please don't contradict the witness on small details.",
  },
  {
    id: 14,
    word: "verdict",
    meaning: "a decision or judgment, especially in court",
    root: "dict",
    rootMeaning: "to say",
    example: "The jury reached a unanimous verdict.",
  },
  {
    id: 15,
    word: "dictionary",
    meaning: "a book that lists words and their meanings",
    root: "dict",
    rootMeaning: "to say",
    example: "I looked up the rare word in a dictionary.",
  },

  // scrib / script — write
  {
    id: 16,
    word: "describe",
    meaning: "to say or write what something is like",
    root: "scrib",
    rootMeaning: "to write",
    example: "Can you describe the stranger you saw?",
  },
  {
    id: 17,
    word: "manuscript",
    meaning: "a document written by hand or an author's draft",
    root: "script",
    rootMeaning: "to write",
    example: "The museum displayed an ancient manuscript.",
  },
  {
    id: 18,
    word: "prescribe",
    meaning: "to officially tell someone to use a medicine or follow a rule",
    root: "scrib",
    rootMeaning: "to write",
    example: "The doctor will prescribe antibiotics for the infection.",
  },
  {
    id: 19,
    word: "transcript",
    meaning: "a written record of spoken words",
    root: "script",
    rootMeaning: "to write",
    example: "She requested a transcript of the lecture.",
  },
  {
    id: 20,
    word: "inscription",
    meaning: "words written or carved on a surface",
    root: "script",
    rootMeaning: "to write",
    example: "The inscription on the monument was nearly worn away.",
  },

  // ject — throw
  {
    id: 21,
    word: "eject",
    meaning: "to force something out",
    root: "ject",
    rootMeaning: "to throw",
    example: "Press the button to eject the disk.",
  },
  {
    id: 22,
    word: "reject",
    meaning: "to refuse to accept",
    root: "ject",
    rootMeaning: "to throw",
    example: "The editor decided to reject the draft.",
  },
  {
    id: 23,
    word: "project",
    meaning: "to throw forward; a planned piece of work",
    root: "ject",
    rootMeaning: "to throw",
    example: "They will project the slides onto the wall.",
  },
  {
    id: 24,
    word: "inject",
    meaning: "to force a liquid into something; to add",
    root: "ject",
    rootMeaning: "to throw",
    example: "Nurses inject vaccines with sterile needles.",
  },
  {
    id: 25,
    word: "subject",
    meaning: "a topic; a person under authority",
    root: "ject",
    rootMeaning: "to throw",
    example: "History is my favorite subject this year.",
  },

  // struct — build
  {
    id: 26,
    word: "construct",
    meaning: "to build or put together",
    root: "struct",
    rootMeaning: "to build",
    example: "Workers will construct a bridge over the river.",
  },
  {
    id: 27,
    word: "destruct",
    meaning: "to destroy (often used in 'self-destruct')",
    root: "struct",
    rootMeaning: "to build",
    example: "The device will self-destruct after the mission.",
  },
  {
    id: 28,
    word: "instruct",
    meaning: "to teach or give directions",
    root: "struct",
    rootMeaning: "to build",
    example: "Coaches instruct players before every match.",
  },
  {
    id: 29,
    word: "structure",
    meaning: "the way parts are arranged; a building",
    root: "struct",
    rootMeaning: "to build",
    example: "The essay needs a clearer structure.",
  },
  {
    id: 30,
    word: "obstruct",
    meaning: "to block or get in the way",
    root: "struct",
    rootMeaning: "to build",
    example: "Fallen trees obstruct the mountain path.",
  },

  // tract — pull / drag
  {
    id: 31,
    word: "attract",
    meaning: "to pull toward; to draw interest",
    root: "tract",
    rootMeaning: "to pull",
    example: "Bright colors attract hummingbirds to the garden.",
  },
  {
    id: 32,
    word: "extract",
    meaning: "to pull out or remove",
    root: "tract",
    rootMeaning: "to pull",
    example: "Dentists extract teeth only when necessary.",
  },
  {
    id: 33,
    word: "contract",
    meaning: "to shrink; a legal agreement",
    root: "tract",
    rootMeaning: "to pull",
    example: "Muscles contract when you lift a weight.",
  },
  {
    id: 34,
    word: "distract",
    meaning: "to pull attention away",
    root: "tract",
    rootMeaning: "to pull",
    example: "Phone alerts distract students during class.",
  },
  {
    id: 35,
    word: "retract",
    meaning: "to pull back; to take back a statement",
    root: "tract",
    rootMeaning: "to pull",
    example: "The cat can retract its claws.",
  },

  // form — shape
  {
    id: 36,
    word: "transform",
    meaning: "to change shape or character completely",
    root: "form",
    rootMeaning: "shape",
    example: "Practice can transform timid speakers into confident ones.",
  },
  {
    id: 37,
    word: "reform",
    meaning: "to improve by changing",
    root: "form",
    rootMeaning: "shape",
    example: "Leaders hope to reform the school lunch program.",
  },
  {
    id: 38,
    word: "conform",
    meaning: "to follow rules or match a standard",
    root: "form",
    rootMeaning: "shape",
    example: "All bags must conform to airline size limits.",
  },
  {
    id: 39,
    word: "deform",
    meaning: "to spoil the shape of something",
    root: "form",
    rootMeaning: "shape",
    example: "Heat can deform cheap plastic bottles.",
  },
  {
    id: 40,
    word: "formulate",
    meaning: "to create or express carefully",
    root: "form",
    rootMeaning: "shape",
    example: "Scientists formulate a hypothesis before testing.",
  },

  // cred — believe
  {
    id: 41,
    word: "credit",
    meaning: "praise; belief that someone will pay later",
    root: "cred",
    rootMeaning: "to believe",
    example: "She deserves credit for solving the bug.",
  },
  {
    id: 42,
    word: "incredible",
    meaning: "hard to believe; amazing",
    root: "cred",
    rootMeaning: "to believe",
    example: "The view from the ridge was incredible.",
  },
  {
    id: 43,
    word: "credible",
    meaning: "believable; trustworthy",
    root: "cred",
    rootMeaning: "to believe",
    example: "We need a credible source for that claim.",
  },
  {
    id: 44,
    word: "credentials",
    meaning: "proof of skills or identity",
    root: "cred",
    rootMeaning: "to believe",
    example: "Bring your credentials to the interview.",
  },
  {
    id: 45,
    word: "discredit",
    meaning: "to harm someone's reputation; to cast doubt",
    root: "cred",
    rootMeaning: "to believe",
    example: "Rumors were spread to discredit the candidate.",
  },

  // graph — write / draw
  {
    id: 46,
    word: "biography",
    meaning: "the written story of someone's life",
    root: "graph",
    rootMeaning: "to write",
    example: "I read a biography of Marie Curie.",
  },
  {
    id: 47,
    word: "autograph",
    meaning: "a person's own signature",
    root: "graph",
    rootMeaning: "to write",
    example: "Fans waited for the singer's autograph.",
  },
  {
    id: 48,
    word: "telegraph",
    meaning: "an old system for sending messages over wires",
    root: "graph",
    rootMeaning: "to write",
    example: "News once traveled by telegraph across oceans.",
  },
  {
    id: 49,
    word: "paragraph",
    meaning: "a group of sentences about one idea",
    root: "graph",
    rootMeaning: "to write",
    example: "Start a new paragraph when the topic shifts.",
  },
  {
    id: 50,
    word: "graphic",
    meaning: "related to visual art; vividly detailed",
    root: "graph",
    rootMeaning: "to write",
    example: "The report included a clear graphic of the data.",
  },
];

export function getRoots(): string[] {
  const seen = new Set<string>();
  const roots: string[] = [];
  for (const item of VOCAB) {
    if (!seen.has(item.root)) {
      seen.add(item.root);
      roots.push(item.root);
    }
  }
  return roots;
}

export function wordsByRoot(root: string): VocabWord[] {
  return VOCAB.filter((w) => w.root === root);
}

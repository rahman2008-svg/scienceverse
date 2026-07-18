package com.example.data

import androidx.compose.ui.geometry.Offset

data class Scientist(
    val id: String,
    val name: String,
    val bengaliName: String,
    val era: String, // Ancient, Medieval, Renaissance, 1700s, 1800s, 1900s, 2000+, Contemporary
    val field: String, // Physics, Chemistry, Biology, Astronomy, Mathematics, Medicine, Computer Science, Engineering, Earth Science
    val birthDate: String,
    val deathDate: String?,
    val birthPlace: String,
    val nationality: String,
    val education: String,
    val career: String,
    val discoveries: List<String>,
    val quotes: List<String>,
    val funFacts: List<String>,
    val childhood: String,
    val struggles: String,
    val successes: String,
    val failures: String,
    val laterLife: String,
    val booksAndPapers: List<BookOrPaper>,
    val teachers: List<String>,
    val coWorkers: List<String>,
    val mapPosition: Offset, // coordinates on the interactive world map [0f..1f]
    val hasNobel: Boolean,
    val nobelYear: Int? = null,
    val nobelCategory: String? = null,
    val nobelResearch: String? = null
)

data class BookOrPaper(
    val title: String,
    val year: String,
    val summary: String
)

data class Discovery(
    val id: String,
    val name: String,
    val bengaliName: String,
    val discoverer: String,
    val history: String,
    val howItWorks: String,
    val currentUsage: String,
    val year: String,
    val field: String,
    val parentDiscoveryId: String? = null, // for Discovery Tree
    val childDiscoveryId: String? = null  // for Discovery Tree
)

data class TimelineEvent(
    val id: String,
    val year: String,
    val name: String,
    val description: String,
    val category: String, // e.g. "Space", "Physics", "Invention"
    val visualPrompt: String
)

data class LaboratoryStory(
    val id: String,
    val name: String,
    val location: String,
    val history: String,
    val keyDiscoveries: List<String>,
    val amazingAnecdote: String
)

data class MuseumExhibit(
    val id: String,
    val name: String,
    val bengaliName: String,
    val description: String,
    val interactiveSimulatorName: String,
    val parts: List<String>,
    val dynamicFormula: String? = null
)

data class QuizQuestion(
    val question: String,
    val options: List<String>,
    val correctAnswerIndex: Int,
    val explanation: String,
    val category: String // "Scientist", "Discovery", "Timeline", "Nobel"
)

object ScienceDatabase {

    val discoveries = listOf(
        Discovery(
            id = "gravity",
            name = "Gravity & Laws of Motion",
            bengaliName = "মহাকর্ষ ও গতির সূত্র",
            discoverer = "Isaac Newton",
            history = "In 1666, Isaac Newton observed an apple falling from a tree in Woolsthorpe Manor, prompting him to wonder why objects fall straight to the ground. This led to his formulation of the universal law of gravitation.",
            howItWorks = "Every particle of matter in the universe attracts every other particle with a force that is directly proportional to the product of their masses and inversely proportional to the square of the distance between their centers.",
            currentUsage = "Used in space exploration, rocket launches, satellite orbits, civil engineering, and understanding cosmology.",
            year = "1687",
            field = "Physics"
        ),
        Discovery(
            id = "relativity",
            name = "Theory of Relativity",
            bengaliName = "আপেক্ষিকতার তত্ত্ব",
            discoverer = "Albert Einstein",
            history = "Albert Einstein published Special Relativity in 1905 and General Relativity in 1915, revolutionizing how we understand space, time, and gravity, showing that space and time are interwoven into a 4D spacetime fabric.",
            howItWorks = "Mass and energy warp the fabric of spacetime, and this curvature is what we perceive as gravity. Time slows down for objects moving close to the speed of light or near massive gravity fields.",
            currentUsage = "Critical for GPS navigation systems, astrophysics, black hole exploration, and cosmology.",
            year = "1915",
            field = "Physics",
            parentDiscoveryId = "gravity"
        ),
        Discovery(
            id = "evolution",
            name = "Natural Selection & Evolution",
            bengaliName = "প্রাকৃতিক নির্বাচন ও বিবর্তনবাদ",
            discoverer = "Charles Darwin",
            history = "Charles Darwin's 5-year voyage on the HMS Beagle (especially to the Galápagos Islands) led him to write 'On the Origin of Species' in 1859, describing how species adapt over generations.",
            howItWorks = "Organisms with traits better suited to their environment are more likely to survive and reproduce, passing those advantageous traits to their offspring.",
            currentUsage = "Foundation of modern biology, genetics, agricultural breeding, pharmacology, and understanding biodiversity.",
            year = "1859",
            field = "Biology"
        ),
        Discovery(
            id = "penicillin",
            name = "Penicillin",
            bengaliName = "পেনিসিলিন",
            discoverer = "Alexander Fleming",
            history = "In 1928, Fleming returned to his messy lab to find a mold (Penicillium notatum) had contaminated a petri dish of Staphylococcus bacteria, and had killed the surrounding bacteria.",
            howItWorks = "It inhibits the formation of peptidoglycan cross-links in the bacterial cell wall, weakening the wall and causing the bacterium to burst (lyse) and die.",
            currentUsage = "First mass-produced antibiotic, saved millions of lives in WWII, remains a crucial tool in treating bacterial infections.",
            year = "1928",
            field = "Medicine"
        ),
        Discovery(
            id = "dna_structure",
            name = "DNA Double Helix Structure",
            bengaliName = "ডিএনএ দ্বিসূত্রক গঠন",
            discoverer = "Watson, Crick & Franklin",
            history = "Rosalind Franklin's 'Photo 51' X-ray diffraction image in 1952 provided key clues that enabled James Watson and Francis Crick to model the double-helix structure in 1953.",
            howItWorks = "Two long strands of nucleotides twist around each other, held together by hydrogen bonds between complementary base pairs (Adenine-Thymine, Cytosine-Guanine), carrying genetic codes.",
            currentUsage = "Enables genetic engineering, forensic DNA profiling, personalized medicine, evolutionary biology, and biotechnology.",
            year = "1953",
            field = "Biology"
        ),
        Discovery(
            id = "radioactivity",
            name = "Radioactivity",
            bengaliName = "তেজস্ক্রিয়তা",
            discoverer = "Marie Curie & Henri Becquerel",
            history = "Becquerel discovered uranium emitted rays in 1896. Marie and Pierre Curie investigated further, discovering Polonium and Radium, coined the term 'radioactivity', and developed techniques to isolate radioactive isotopes.",
            howItWorks = "Unstable atomic nuclei lose energy by emitting ionizing particles (alpha, beta, gamma) to achieve a more stable state.",
            currentUsage = "Used in cancer radiation therapy, nuclear energy generation, radiocarbon dating, and industrial sterilization.",
            year = "1898",
            field = "Chemistry"
        ),
        Discovery(
            id = "quantum",
            name = "Quantum Mechanics",
            bengaliName = "কোয়ান্টাম মেকানিক্স",
            discoverer = "Max Planck & Niels Bohr",
            history = "Max Planck proposed in 1900 that energy is emitted in discrete packets called 'quanta' to solve the blackbody radiation problem, breaking with classical physics.",
            howItWorks = "Describes the physical properties of nature at the scale of atoms and subatomic particles, introducing wave-particle duality, quantization, and the uncertainty principle.",
            currentUsage = "Powers all modern electronics, semiconductor microchips, lasers, MRI scanners, and quantum computing.",
            year = "1900",
            field = "Physics",
            parentDiscoveryId = "relativity"
        )
    )

    val scientists = listOf(
        Scientist(
            id = "einstein",
            name = "Albert Einstein",
            bengaliName = "আলবার্ট আইনস্টাইন",
            era = "1900s",
            field = "Physics",
            birthDate = "March 14, 1879",
            deathDate = "April 18, 1955",
            birthPlace = "Ulm, Kingdom of Württemberg, German Empire",
            nationality = "German, Swiss, American",
            education = "ETH Zurich, University of Zurich",
            career = "Patent clerk in Bern, Professor at Charles University of Prague, Princeton Institute for Advanced Study",
            discoveries = listOf("Theory of Relativity", "Photoelectric Effect", "E=mc² Mass-Energy Equivalence", "Brownian Motion"),
            quotes = listOf(
                "Imagination is more important than knowledge.",
                "Two things are infinite: the universe and human stupidity; and I'm not sure about the universe.",
                "Life is like riding a bicycle. To keep your balance, you must keep moving."
            ),
            funFacts = listOf(
                "He never wore socks because he thought they were a nuisance.",
                "He did not talk until he was nearly 4 years old, prompting concern from his parents.",
                "The pathologist who did his autopsy stole his brain and kept it in a jar for decades."
            ),
            childhood = "Albert was a quiet child who loved playing the violin and building card houses. He disliked the rigid, militaristic rote-learning of German schools and found comfort in geometry and classical music.",
            struggles = "He struggled to find an academic post after graduating from Zurich polytechnic because his professors disliked his rebellious attitude. He worked as a patent clerk, doing his revolutionary physics in his spare time.",
            successes = "His 'Annus Mirabilis' (Miracle Year) in 1905 changed physics forever with 4 papers. He predicted gravitational lensing and gravitational waves, both proven correct decades later.",
            failures = "He spent the last 30 years of his life trying to formulate a 'Unified Field Theory' combining gravity and electromagnetism but failed, partly due to rejecting quantum entanglement.",
            laterLife = "Escaped Nazi Germany in 1933, settled in Princeton, New Jersey. He actively campaigned for nuclear disarmament and civil rights until his death in 1955.",
            booksAndPapers = listOf(
                BookOrPaper("On the Electrodynamics of Moving Bodies", "1905", "Introduced the Special Theory of Relativity, linking space and time."),
                BookOrPaper("The Evolution of Physics", "1938", "A popular science book tracing the history of physical ideas from Galileo to Quantum.")
            ),
            teachers = listOf("Heinrich Weber", "Hermann Minkowski"),
            coWorkers = listOf("Mileva Marić", "Michele Besso", "Nathan Rosen", "Boris Podolsky"),
            mapPosition = Offset(0.48f, 0.32f),
            hasNobel = true,
            nobelYear = 1921,
            nobelCategory = "Physics",
            nobelResearch = "For his services to Theoretical Physics, and especially for his discovery of the law of the photoelectric effect."
        ),
        Scientist(
            id = "newton",
            name = "Isaac Newton",
            bengaliName = "আইজ্যাক নিউটন",
            era = "Renaissance",
            field = "Physics",
            birthDate = "January 4, 1643",
            deathDate = "March 31, 1727",
            birthPlace = "Woolsthorpe-by-Colsterworth, Lincolnshire, England",
            nationality = "English",
            education = "Trinity College, Cambridge",
            career = "Lucasian Professor of Mathematics, Warden of the Royal Mint, President of the Royal Society",
            discoveries = listOf("Universal Gravitation", "Calculus", "Three Laws of Motion", "Reflecting Telescope", "Optics of Light Spectrum"),
            quotes = listOf(
                "If I have seen further, it is by standing on the shoulders of giants.",
                "I can calculate the motion of heavenly bodies, but not the madness of people.",
                "Truth is ever to be found in simplicity, and not in the multiplicity and confusion of things."
            ),
            funFacts = listOf(
                "He was born prematurely and was so small he could fit into a quart mug.",
                "He was highly paranoid and spent decades in bitter priority disputes with Leibniz over Calculus and Hooke over Gravity.",
                "He was deeply into alchemy and spent more time writing about theology than physics."
            ),
            childhood = "His father died before he was born, and his mother left him with his grandmother. He was a quiet, inventive boy who built waterclocks and windmills, but was considered a poor student initially.",
            struggles = "The Great Plague of London closed Cambridge University in 1665. Newton returned home and worked in isolation, producing his most profound discoveries on calculus, optics, and gravity.",
            successes = "Published 'Philosophiae Naturalis Principia Mathematica' in 1687, widely regarded as the most influential book in the history of science.",
            failures = "Lost a fortune in the South Sea Bubble stock market crash, leading him to remark on the 'madness of people.'",
            laterLife = "Lived in London as Master of the Mint, vigorously hunting down counterfeiters. He was knighted by Queen Anne in 1705 and buried in Westminster Abbey with full national honors.",
            booksAndPapers = listOf(
                BookOrPaper("Principia Mathematica", "1687", "Formulated the laws of motion and universal gravitation that defined classical mechanics."),
                BookOrPaper("Opticks", "1704", "Analyzed the fundamental nature of light and color using prisms.")
            ),
            teachers = listOf("Isaac Barrow"),
            coWorkers = listOf("Edmond Halley", "Robert Hooke (rival)"),
            mapPosition = Offset(0.46f, 0.28f),
            hasNobel = false
        ),
        Scientist(
            id = "curie",
            name = "Marie Curie",
            bengaliName = "মারি কুরি",
            era = "1800s",
            field = "Chemistry",
            birthDate = "November 7, 1867",
            deathDate = "July 4, 1934",
            birthPlace = "Warsaw, Kingdom of Poland (Russian Empire)",
            nationality = "Polish, French",
            education = "Flying University, Sorbonne University",
            career = "Director of the Curie Laboratory, Professor at Sorbonne University (first female professor)",
            discoveries = listOf("Discovery of Radium & Polonium", "Coined Radioactivity", "Mobile X-Ray units for WWI"),
            quotes = listOf(
                "Nothing in life is to be feared, it is only to be understood. Now is the time to understand more, so that we may fear less.",
                "Be less curious about people and more curious about ideas.",
                "You cannot hope to build a better world without improving the individuals."
            ),
            funFacts = listOf(
                "She is the only person to win Nobel Prizes in two different scientific fields (Physics & Chemistry).",
                "Her laboratory notebooks are still so radioactive today that they must be kept in lead-lined boxes and handled with protective gear.",
                "She named the first element she discovered, Polonium, after her beloved homeland, Poland."
            ),
            childhood = "Born Maria Salomea Skłodowska in Warsaw. Her family lost their fortune supporting Polish independence. Since women couldn't attend university in Poland, she worked as a governess to pay for her sister's medical school.",
            struggles = "Lived in a tiny, freezing attic in Paris, surviving on tea and bread. In her research, she and Pierre stirred tons of pitchblende waste in a drafty, damp shed without safety equipment.",
            successes = "Isolated pure radium, proved radioactivity is an atomic property. Awarded Nobel Prizes in 1903 (Physics) and 1911 (Chemistry).",
            failures = "Faced intense sexism and xenophobia from the French press and Academy. She was denied election to the French Academy of Sciences by two votes.",
            laterLife = "Established the Radium Institutes in Paris and Warsaw. During WWI, she developed mobile radiography vehicles ('Little Curies') to help surgeons on the front lines. Died of aplastic anemia from radiation exposure.",
            booksAndPapers = listOf(
                BookOrPaper("Researches on Radioactive Substances", "1903", "Her doctoral thesis describing her discovery of radium and polonium.")
            ),
            teachers = listOf("Gabriel Lippmann", "Henri Becquerel"),
            coWorkers = listOf("Pierre Curie", "Irène Joliot-Curie"),
            mapPosition = Offset(0.49f, 0.31f),
            hasNobel = true,
            nobelYear = 1903,
            nobelCategory = "Physics / Chemistry",
            nobelResearch = "For her services in the joint research of radiation phenomena (1903) & discovery of radium and polonium (1911)."
        ),
        Scientist(
            id = "darwin",
            name = "Charles Darwin",
            bengaliName = "চার্লস ডারউইন",
            era = "1800s",
            field = "Biology",
            birthDate = "February 12, 1809",
            deathDate = "April 19, 1882",
            birthPlace = "Shrewsbury, Shropshire, England",
            nationality = "English",
            education = "University of Edinburgh, Christ's College, Cambridge",
            career = "Naturalist on the HMS Beagle, Independent researcher and author",
            discoveries = listOf("Theory of Evolution", "Natural Selection", "Branching Common Descent"),
            quotes = listOf(
                "It is not the strongest of the species that survives, nor the most intelligent, but the one most responsive to change.",
                "The love for all living creatures is the most noble attribute of man.",
                "Ignorance more frequently begets confidence than does knowledge."
            ),
            funFacts = listOf(
                "He shared a birthday with Abraham Lincoln.",
                "He was an adventurous eater, eating owls, iguanas, and giant tortoises during his travels.",
                "He delayed publishing his theory for over 20 years because he feared the religious and social backlash."
            ),
            childhood = "An affluent childhood, Darwin loved collecting insects and exploring the woods. His father, a wealthy doctor, thought Charles was a slacker and sent him to medical school, which Charles hated because he couldn't stand blood.",
            struggles = "He suffered from mysterious, debilitating chronic illnesses (likely Chagas disease or severe anxiety) for most of his adult life, causing extreme fatigue, vomiting, and skin issues.",
            successes = "His voyage on the Beagle gave him massive specimens. He formulated Natural Selection and finally co-published with Alfred Russel Wallace when Wallace arrived at the same idea independently.",
            failures = "He lacked a genetic mechanism for inheritance, which was being discovered by Gregor Mendel at the exact same time but remained unknown to Darwin.",
            laterLife = "Lived quietly at Down House in Kent, writing about earthworms, orchids, and human evolution. He was buried in Westminster Abbey, close to Isaac Newton.",
            booksAndPapers = listOf(
                BookOrPaper("On the Origin of Species", "1859", "Introduced the theory that populations evolve over generations through natural selection."),
                BookOrPaper("The Descent of Man", "1871", "Applied evolutionary theory to human beings, tracing our ancestry and sexual selection.")
            ),
            teachers = listOf("John Stevens Henslow", "Adam Sedgwick"),
            coWorkers = listOf("Alfred Russel Wallace", "Thomas Henry Huxley"),
            mapPosition = Offset(0.45f, 0.29f),
            hasNobel = false
        ),
        Scientist(
            id = "turing",
            name = "Alan Turing",
            bengaliName = "অ্যালান টিউরিং",
            era = "1900s",
            field = "Computer Science",
            birthDate = "June 23, 1912",
            deathDate = "June 7, 1954",
            birthPlace = "Maida Vale, London, England",
            nationality = "English",
            education = "King's College, Cambridge, Princeton University",
            career = "Researcher at Cambridge, Codebreaker at Bletchley Park, Deputy Director of Computing Machine Laboratory at Manchester",
            discoveries = listOf("Turing Machine (Universal Computer Model)", "Bletchley Park Enigma Decryption", "Turing Test for AI", "Morphogenesis in Biology"),
            quotes = listOf(
                "We can only see a short distance ahead, but we can see plenty there that needs to be done.",
                "Sometimes it is the people no one can imagine anything of who do the things no one can imagine.",
                "A computer would deserve to be called intelligent if it could deceive a human into believing that it was human."
            ),
            funFacts = listOf(
                "He was an Olympic-class marathon runner, once running a marathon in 2 hours 46 minutes.",
                "He used to chain his coffee mug to the radiator at Bletchley Park to stop others from taking it.",
                "The famous Turing Award, the 'Nobel Prize of Computing', is named after him."
            ),
            childhood = "Showed early genius in mathematics, solving advanced problems without learning calculus. He was eccentric, often struggling in English and humanities because his teachers didn't appreciate his focus on math.",
            struggles = "In 1952, he was prosecuted for homosexual acts, which were then illegal in the UK. He was forced to undergo chemical castration, losing his security clearance and academic standing.",
            successes = "Designed the 'Bombe', an electromechanical machine that cracked the German naval Enigma code, saving millions of lives in WWII.",
            failures = "His design for the ACE (Automatic Computing Engine), which would have been the first complete stored-program computer, was delayed by bureaucratic inertia, allowing others to build it first.",
            laterLife = "Died in 1954 of cyanide poisoning, ruled as suicide. He received a posthumous royal pardon in 2013, and is now celebrated as the father of modern computer science and artificial intelligence.",
            booksAndPapers = listOf(
                BookOrPaper("On Computable Numbers", "1936", "Invented the Turing Machine, laying the mathematical foundation of all computer science."),
                BookOrPaper("Computing Machinery and Intelligence", "1950", "Introduced the Turing Test, launching the field of Artificial Intelligence.")
            ),
            teachers = listOf("Alonzo Church", "Max Newman"),
            coWorkers = listOf("Gordon Welchman", "Joan Clarke", "Donald Michie"),
            mapPosition = Offset(0.46f, 0.30f),
            hasNobel = false
        ),
        Scientist(
            id = "ramanujan",
            name = "Srinivasa Ramanujan",
            bengaliName = "শ্রীনিবাস রামানুজন",
            era = "1900s",
            field = "Mathematics",
            birthDate = "December 22, 1887",
            deathDate = "April 26, 1920",
            birthPlace = "Erode, Madras Presidency, British India",
            nationality = "Indian",
            education = "Government Arts College, Trinity College, Cambridge",
            career = "Clerk at Madras Port Trust, Fellow of the Royal Society",
            discoveries = listOf("Ramanujan Theta Function", "Partition Formula", "Ramanujan Prime", "Infinite series for Pi"),
            quotes = listOf(
                "An equation for me has no meaning unless it expresses a thought of God.",
                "No, Mr. Hardy, it is a very interesting number; it is the smallest number expressible as the sum of two cubes in two different ways."
            ),
            funFacts = listOf(
                "The famous story of Hardy visiting him in a taxi: taxi number 1729 was 'dull', but Ramanujan instantly noted it's the smallest taxicab number (1729 = 1³ + 12³ = 9³ + 10³).",
                "He claimed his family goddess, Namagiri Thayar, wrote the mathematical formulas on his tongue in dreams.",
                "He went to England without formal qualifications and compiled nearly 3,900 mathematical identities and equations."
            ),
            childhood = "Born to a poor Brahmin family. At age 11, he exhausted the mathematical knowledge of two college students lodger in his home. He borrowed a library copy of Carr's Synopsis of Elementary Results and mastered it.",
            struggles = "Failed his non-mathematical subjects in college because he refused to study anything else, losing his scholarship. He lived in extreme poverty, writing math formulas on scrap papers with red ink.",
            successes = "Sent a letter with 120 theorems to G.H. Hardy at Cambridge, who recognized his absolute genius. Hardy invited him to Cambridge, where they published legendary papers together.",
            failures = "Due to lack of formal training, many of his early proofs were unstructured, and some formulas he derived had already been discovered, though he found them independently.",
            laterLife = "Fell severely ill in England (likely tuberculosis or amoebiasis). Returned to India in 1919 and died in 1920 at the young age of 32, working on mock theta functions on his deathbed.",
            booksAndPapers = listOf(
                BookOrPaper("Ramanujan's Notebooks", "1912-1920", "Three large notebooks containing thousands of results discovered without proofs.")
            ),
            teachers = listOf("G. H. Hardy", "J. E. Littlewood"),
            coWorkers = listOf("G. H. Hardy"),
            mapPosition = Offset(0.68f, 0.45f),
            hasNobel = false
        ),
        Scientist(
            id = "s_bose",
            name = "Satyendra Nath Bose",
            bengaliName = "সত্যেন্দ্রনাথ বসু",
            era = "1900s",
            field = "Physics",
            birthDate = "January 1, 1894",
            deathDate = "February 4, 1974",
            birthPlace = "Calcutta, Bengal Presidency, British India",
            nationality = "Bangladeshi, Indian",
            education = "Presidency College, Calcutta",
            career = "Reader and Head of Physics at University of Dhaka, Professor at Calcutta University",
            discoveries = listOf("Bose-Einstein Statistics", "Prediction of Bose-Einstein Condensate", "Boson (fundamental particles named after him)"),
            quotes = listOf(
                "If a theory is not beautiful, it is probably not correct.",
                "The world is wonderful and mysterious. We are only scratching the surface."
            ),
            funFacts = listOf(
                "Half of the fundamental particles in the universe, 'Bosons', are named in his honor by Paul Dirac.",
                "He sent his paper directly to Albert Einstein after European journals rejected it. Einstein translated it into German himself and got it published.",
                "He was incredibly polymathic, translating French and German literature, playing the Esraj (instrument), and studying chemistry and botany."
            ),
            childhood = "The eldest of seven children, Satyendra was brilliant. His math teacher in school once gave him 110 marks out of 100 because he had solved algebra problems in multiple unique ways.",
            struggles = "Dhaka University was far from the global centers of physics. Access to current research papers was heavily delayed. When he made his breakthrough, western publishers didn't believe an Indian scholar could correct Max Planck.",
            successes = "Developed a new statistical law for light particles (photons) which laid the foundation for quantum statistics. Einstein adopted it, leading to the Bose-Einstein statistics.",
            failures = "He did not receive the Nobel Prize, a decision many physicists consider one of the greatest omissions in Nobel history, though multiple Nobel Prizes were awarded for work on Bosons and BEC.",
            laterLife = "Served as Dhaka University's Head of Physics. Later returned to Calcutta. He was nominated as a National Professor of India and remained a patron of regional language science publications.",
            booksAndPapers = listOf(
                BookOrPaper("Planck's Law and Light Quantum Hypothesis", "1924", "Derivation of Planck's radiation law without classical electrodynamics, founding Boson statistics.")
            ),
            teachers = listOf("Jagadish Chandra Bose", "Prafulla Chandra Ray"),
            coWorkers = listOf("Albert Einstein", "Meghnad Saha"),
            mapPosition = Offset(0.70f, 0.43f),
            hasNobel = false
        ),
        Scientist(
            id = "j_islam",
            name = "Jamal Nazrul Islam",
            bengaliName = "জামাল নজরুল ইসলাম",
            era = "Contemporary",
            field = "Astronomy",
            birthDate = "February 24, 1939",
            deathDate = "March 16, 2013",
            birthPlace = "Jhenaidah, Bengal Presidency, British India",
            nationality = "Bangladeshi",
            education = "University of Calcutta, Trinity College, Cambridge",
            career = "Professor at Cardiff, Researcher at Institute for Advanced Study (Princeton), Professor and Director of RCMPS at Chittagong University",
            discoveries = listOf("The Ultimate Fate of the Universe", "General Relativity Rotating Fields", "Cosmological Constants"),
            quotes = listOf(
                "I returned to Bangladesh because I wanted to build a scientific culture in my own country. A scientist's duty is not just to research, but to inspire.",
                "The universe is an open book written in the language of mathematics."
            ),
            funFacts = listOf(
                "His book 'The Ultimate Fate of the Universe' was translated into Japanese, French, Portuguese, and Russian and was praised by Stephen Hawking.",
                "He was a close friend of Stephen Hawking, Richard Feynman, and Freeman Dyson at Cambridge.",
                "He left high-paying research positions in the US and UK to return to Chittagong University, drawing a fraction of his western salary."
            ),
            childhood = "Born in a prominent family. He studied in Chittagong, then went to Kolkata, and eventually to Cambridge. He showed profound talent in mathematical physics and painting.",
            struggles = "Faced lack of infrastructure, research funding, and computing resources when he returned to Bangladesh in 1984. He established the Research Center for Mathematical and Physical Sciences (RCMPS) single-handedly.",
            successes = "Calculated the long-term future of the universe, predicting that stars will burn out, black holes will evaporate, and matter itself will decay.",
            failures = "Struggled with local university bureaucracy, which failed to understand the importance of theoretical research and centers of excellence.",
            laterLife = "Lived in Chittagong, writing books on popular science, environment, and economy. He was an avid piano player, artist, and philanthropist until his death in 2013.",
            booksAndPapers = listOf(
                BookOrPaper("The Ultimate Fate of the Universe", "1983", "First comprehensive scientific book on the long-term cosmic future, detailing proton decay and black hole evaporation."),
                BookOrPaper("Classical Cosmology", "1995", "Highly acclaimed mathematical introduction to cosmological models.")
            ),
            teachers = listOf("Fred Hoyle"),
            coWorkers = listOf("Stephen Hawking", "Jayant Narlikar", "Brandon Carter"),
            mapPosition = Offset(0.70f, 0.44f),
            hasNobel = false
        ),
        Scientist(
            id = "ibn_sina",
            name = "Ibn Sina (Avicenna)",
            bengaliName = "ইবনে সিনা",
            era = "Medieval",
            field = "Medicine",
            birthDate = "980 AD",
            deathDate = "1037 AD",
            birthPlace = "Afshana near Bukhara, Samanid Empire (Modern Uzbekistan)",
            nationality = "Persian",
            education = "Bukhara Royal Library",
            career = "Royal Physician to Samanid Emirs, Vizier to Kakuyid rulers, Polymath researcher",
            discoveries = listOf("The Canon of Medicine", "Infectious nature of diseases", "Quarantine to stop disease spread", "Clinical pharmacology"),
            quotes = listOf(
                "The knowledge of anything, since all things have causes, is not acquired or complete unless it is known by its causes.",
                "There are no incurable diseases, only the lack of will.",
                "Width of life is more important than length of life."
            ),
            funFacts = listOf(
                "He had memorized the entire Quran by the age of 10.",
                "His book 'The Canon of Medicine' remained the standard medical textbook in Europe and the Islamic world for over 600 years.",
                "He diagnosed love sickness (extreme infatuation) as a clinical psychiatric disorder by monitoring heart rates."
            ),
            childhood = "An absolute child prodigy. By 16, he was treating patients and developing new medical methods. He read Aristotle's Metaphysics 40 times until he had memorized it, finally understanding it after reading Al-Farabi's commentary.",
            struggles = "Lived in a highly turbulent period of Central Asia. He had to flee Bukhara after the fall of the Samanids, traveling from town to town, writing his books on horseback and in prisons.",
            successes = "Authored 'The Book of Healing' (a massive philosophical encyclopedia) and 'The Canon of Medicine', representing the pinnacle of medieval science.",
            failures = "Fell out of favor with several local rulers due to political intrigue, leading to brief imprisonments and the loss of some of his research manuscripts.",
            laterLife = "Settled in Isfahan as physician and advisor. Died of colic in 1037 AD and was buried in Hamadan, Iran, widely mourned as 'Al-Sheikh al-Rais' (The Prince of Physicians).",
            booksAndPapers = listOf(
                BookOrPaper("The Canon of Medicine", "1025", "Five-volume medical encyclopedia that systematized ancient and Islamic medical knowledge."),
                BookOrPaper("The Book of Healing", "1027", "A philosophical and scientific encyclopedia covering logic, physics, math, and astronomy.")
            ),
            teachers = listOf("Abu Sahl al-Masihi"),
            coWorkers = listOf("Al-Biruni (correspondence)"),
            mapPosition = Offset(0.58f, 0.35f),
            hasNobel = false
        )
    ) + moreScientistsPart1 + moreScientistsPart2 + moreScientistsPart3 + moreScientistsPart4

    val timelineEvents = listOf(
        TimelineEvent("1", "Prehistory", "Discovery of Fire", "Early humans tamed fire, enabling cooking, warmth, and protection, marking the dawn of technology.", "Invention", "Cavemen rubbing stones, sparking flame"),
        TimelineEvent("2", "3500 BC", "Invention of the Wheel", "Invented in Mesopotamia, the wheel revolutionized transport, trade, and mechanics.", "Invention", "Wooden round wheel on a simple cart"),
        TimelineEvent("3", "1609", "First Telescope", "Galileo Galilei pointed his newly crafted refracting telescope to the sky, discovering Jupiter's moons and lunar craters.", "Space", "Galileo holding a wooden tube with glass lenses"),
        TimelineEvent("4", "1769", "Watt Steam Engine", "James Watt patented a separate condenser for the steam engine, launching the Industrial Revolution.", "Invention", "Giant iron piston driven by steam and coal fire"),
        TimelineEvent("5", "1831", "Electromagnetic Induction", "Michael Faraday discovered moving a magnet through a wire loop generates electricity, founding modern power grid technology.", "Physics", "Copper wire coil, magnet, and galvanometer needle"),
        TimelineEvent("6", "1947", "Invention of the Transistor", "Shockley, Bardeen, and Brattain at Bell Labs invented the solid-state transistor, replacing vacuum tubes and founding modern computing.", "Physics", "Tiny metal and germanium contact point transistor on a board"),
        TimelineEvent("7", "1957", "Sputnik 1 Launch", "The Soviet Union launched the first artificial satellite, Sputnik 1, into orbit, kicking off the Space Age.", "Space", "Metallic sphere with four antennae orbiting Earth"),
        TimelineEvent("8", "1969", "Apollo 11 Moon Landing", "Neil Armstrong and Buzz Aldrin became the first humans to walk on the Moon, a historic triumph of science and engineering.", "Space", "Astronaut footprint in grey lunar soil, Lunar Module behind"),
        TimelineEvent("9", "1989", "World Wide Web", "Tim Berners-Lee invented HTML, HTTP, and URLs at CERN, creating the modern Internet which connects the globe.", "Invention", "NeXT computer with a sticker reading 'This machine is a server'"),
        TimelineEvent("10", "2015", "Gravitational Waves Detected", "LIGO observed ripples in spacetime from colliding black holes, confirming Einstein's 100-year-old prediction.", "Space", "Two black holes spiraling together, creating ripples in a grid")
    )

    val laboratoryStories = listOf(
        LaboratoryStory(
            id = "cern",
            name = "CERN (European Organization for Nuclear Research)",
            location = "Geneva, Switzerland",
            history = "Founded in 1954, CERN is the world's largest particle physics laboratory. It spans the border between France and Switzerland and operates the Large Hadron Collider (LHC) inside a 27-kilometer circular tunnel.",
            keyDiscoveries = listOf("Higgs Boson (2012)", "W and Z bosons (1983)", "Creation of Antihydrogen (1995)", "World Wide Web (1989)"),
            amazingAnecdote = "The World Wide Web was invented at CERN by Tim Berners-Lee to help physicists share information across global universities. The original server was a NeXT computer which was left with a warning note: 'This machine is a server. DO NOT POWER IT DOWN!'"
        ),
        LaboratoryStory(
            id = "curie_lab",
            name = "Curie Laboratory (Paris)",
            location = "Paris, France",
            history = "Established for Marie Curie by the Pasteur Institute and Sorbonne in 1914. It became a global epicenter for the study of radioactivity, directed by Marie, and later by her daughter Irène.",
            keyDiscoveries = listOf("Isolation of Pure Radium", "Artificial Radioactivity", "Alpha-particle decay mechanism"),
            amazingAnecdote = "The Curie lab ran on family devotion. Irène and Frédéric Joliot-Curie discovered artificial radioactivity there in 1934, winning a Nobel Prize. The lab notebooks were so contaminated that Marie's door handles and desks still register high radioactive clicks on Geiger counters today."
        ),
        LaboratoryStory(
            id = "bell_labs",
            name = "Bell Labs",
            location = "Murray Hill, New Jersey, USA",
            history = "Established as Bell Telephone Laboratories in 1925, it was the R&D arm of AT&T. It became famous for bringing researchers together from physics, mathematics, and chemistry to create breakthroughs.",
            keyDiscoveries = listOf("The Transistor", "Information Theory", "Cosmic Microwave Background (CMB)", "C Programming Language", "Unix Operating System"),
            amazingAnecdote = "Arno Penzias and Robert Wilson discovered a persistent hiss in their horn antenna in 1964. Believing it was caused by pigeon droppings, they spent days scrubbing the antenna clean. The hiss remained—it was the Cosmic Microwave Background, the echo of the Big Bang, which won them the Nobel Prize."
        ),
        LaboratoryStory(
            id = "nasa",
            name = "NASA (National Aeronautics and Space Administration)",
            location = "USA (Multiple Centers)",
            history = "Established in 1958 by President Eisenhower in response to the Soviet Sputnik launch. NASA has led US space exploration, including Apollo moon missions, Skylab, the Space Shuttle, Hubble, and Mars Rovers.",
            keyDiscoveries = listOf("Apollo 11 Moon Landing", "Voyager interstellar mission", "James Webb Space Telescope (JWST)", "Hubble Deep Field", "Exoplanet catalogs"),
            amazingAnecdote = "The Apollo Guidance Computer, which took astronauts to the moon in 1969, had only 74 kilobytes of ROM memory. Today, a standard smartphone has over 1,000,000 times more RAM than the computer that landed on the moon!"
        )
    )

    val museumExhibits = listOf(
        MuseumExhibit(
            id = "telescope",
            name = "Galilean Telescope",
            bengaliName = "গ্যালিলীয় দূরবীন",
            description = "A historical optical instrument that used a convex objective lens and a concave eyepiece lens to magnify distant objects. Galileo used this to peer into the solar system, changing our place in the cosmos.",
            interactiveSimulatorName = "Lens Focuser",
            parts = listOf("Objective Lens (Convex)", "Eyepiece Lens (Concave)", "Wooden Drawtube", "Focusing Ring"),
            dynamicFormula = "Magnification (M) = -fo / fe"
        ),
        MuseumExhibit(
            id = "microscope",
            name = "Compound Microscope",
            bengaliName = "যৌগিক অণুবীক্ষণ যন্ত্র",
            description = "Developed by Robert Hooke and Anton van Leeuwenhoek, this instrument uses a multi-lens system to reveal microscopic structures, paving the way to discover cells, bacteria, and microscopic organisms.",
            interactiveSimulatorName = "Focal Adjuster",
            parts = listOf("Stage", "Objective Lens", "Ocular Lens", "Coarse Adjustment Knob", "Light Mirror"),
            dynamicFormula = "Total Magnification = M_objective * M_ocular"
        ),
        MuseumExhibit(
            id = "steam_engine",
            name = "Watt's Steam Engine",
            bengaliName = "ওয়াটের বাষ্পীয় ইঞ্জিন",
            description = "The mechanical marvel that drove industrialization. It converts thermal energy of steam into mechanical work using a separate condenser and double-acting piston mechanism.",
            interactiveSimulatorName = "Boiler Pressure Valve",
            parts = listOf("Boiler", "Piston Cylinder", "Separate Condenser", "Flywheel", "Governor Valve"),
            dynamicFormula = "Efficiency (η) = (Th - Tc) / Th"
        ),
        MuseumExhibit(
            id = "dna_model",
            name = "DNA Double Helix Model",
            bengaliName = "ডিএনএ ডাবল হেলিক্স মডেল",
            description = "A physical representation of the deoxyribonucleic acid structure, illustrating the complementary base pairs and sugar-phosphate backbones that store hereditary genetic blueprints.",
            interactiveSimulatorName = "Base Pair Matcher",
            parts = listOf("Phosphate-Sugar Backbone", "Adenine-Thymine (A-T) Pair", "Cytosine-Guanine (C-G) Pair", "Hydrogen Bonds"),
            dynamicFormula = "Chargaff's Rule: A = T, C = G"
        )
    )

    val quizzes = listOf(
        QuizQuestion(
            question = "Who is the only person to win Nobel Prizes in two different scientific fields?",
            options = listOf("Albert Einstein", "Marie Curie", "Linus Pauling", "John Bardeen"),
            correctAnswerIndex = 1,
            explanation = "Marie Curie won the 1903 Nobel Prize in Physics (for radiation research) and the 1911 Nobel Prize in Chemistry (for discovering radium and polonium).",
            category = "Nobel"
        ),
        QuizQuestion(
            question = "Which Bengali physicist's name is commemorated in the name of fundamental particles called 'Bosons'?",
            options = listOf("Jagadish Chandra Bose", "Satyendra Nath Bose", "Meghnad Saha", "Prafulla Chandra Ray"),
            correctAnswerIndex = 1,
            explanation = "Satyendra Nath Bose's work on quantum statistics led Paul Dirac to coin the term 'Bosons' for particles that obey Bose-Einstein statistics.",
            category = "Scientist"
        ),
        QuizQuestion(
            question = "In which year did Charles Darwin publish his groundbreaking book 'On the Origin of Species'?",
            options = listOf("1859", "1882", "1905", "1831"),
            correctAnswerIndex = 0,
            explanation = "Charles Darwin published 'On the Origin of Species' in 1859, proposing the theory of evolution by natural selection.",
            category = "Timeline"
        ),
        QuizQuestion(
            question = "What fundamental biological structure did Rosalind Franklin's Photo 51 help discover?",
            options = listOf("Cell Nucleus", "Ribosome", "DNA Double Helix", "Mitochondria"),
            correctAnswerIndex = 2,
            explanation = "Rosalind Franklin's Photo 51 was an X-ray diffraction image of DNA that clearly indicated its double helix structure, which Watson and Crick used to build their model.",
            category = "Discovery"
        ),
        QuizQuestion(
            question = "What did Arno Penzias and Robert Wilson discover at Bell Labs while trying to clean pigeon droppings from their antenna?",
            options = listOf("Pulsars", "Cosmic Microwave Background (CMB)", "Radio Waves", "Gravitational Waves"),
            correctAnswerIndex = 1,
            explanation = "They discovered a persistent background noise which turned out to be the Cosmic Microwave Background radiation, the heat signature of the Big Bang.",
            category = "Nobel"
        ),
        QuizQuestion(
            question = "Which Bangladeshi astronomer wrote the internationally acclaimed book 'The Ultimate Fate of the Universe'?",
            options = listOf("Dr. Wazed Miah", "Dr. Jamal Nazrul Islam", "Dr. Qudrat-i-Khuda", "Dr. Mohammad Kaykobad"),
            correctAnswerIndex = 1,
            explanation = "Dr. Jamal Nazrul Islam was a world-renowned cosmologist and physicist who authored 'The Ultimate Fate of the Universe' in 1983, published by Cambridge University Press.",
            category = "Scientist"
        ),
        QuizQuestion(
            question = "What antibiotic did Alexander Fleming discover by accident in his contaminated petri dish in 1928?",
            options = listOf("Streptomycin", "Penicillin", "Amoxicillin", "Tetracycline"),
            correctAnswerIndex = 1,
            explanation = "Alexander Fleming discovered Penicillin in 1928, the world's first effective broad-spectrum antibiotic, isolated from Penicillium notatum mold.",
            category = "Discovery"
        )
    )

    // Today in Science Event generator based on date
    fun getTodayInScience(month: Int, day: Int): List<String> {
        // July 18 is today's date in local time metadata (2026-07-18)
        return if (month == 7 && day == 18) {
            listOf(
                "Birth (1635): Robert Hooke, English natural philosopher, architect, and polymath who discovered the cell and formulated Hooke's Law of elasticity.",
                "Discovery (1898): Marie and Pierre Curie announced their discovery of a new radioactive element, which they named 'Polonium' in honor of Marie's home country.",
                "Space (1966): NASA launched Gemini 10, a manned spaceflight mission carrying astronauts John Young and Michael Collins to perform orbital dockings."
            )
        } else {
            listOf(
                "Birth (1918): Nelson Mandela, Nobel Peace laureate, born today.",
                "Invention (1968): Intel Corporation is founded in Mountain View, California, beginning a revolution in microprocessors.",
                "Space (1965): Lunar 8, the Soviet unmanned lunar flyby probe, takes high-resolution photographs of the Moon's far side."
            )
        }
    }
}

import extensions.CSVFile;

class Projet extends Program {
	int score=0;
	int chaine=1; // Plus il y a de reussites, plus le score progresse vite
	int niveau=-1; // Correspond au niveau de connaissance du joueur
	int nbTours=0;
	String nomDuJoueur="";

	void algorithm() {
		welcome();
		delay(600);
		clearScreen();
		cursor(0,0);
		while(!fini(score)) {
			direction();
			choixJeu();
			println("\n\n< APPUYEZ SUR ENTRÉE POUR CONTINUER >");
			readString();
			clearScreen();
			cursor(0,0);
			println("\tVous avez "+score+" trésor(s)");
			nbTours+=1;
		}
		println("La partie est terminée ! Vous avez à cumuler les 50 trésors en "+nbTours+" tours !");
	}

	void welcome(){
		int input=0;
		String inputS="";
		boolean fini=false;
		clearScreen();
		cursor(0,0);
		discours("Welcome in Piratopia !\n\nTu as l'air nouveau, alors je vais te guider. Je suis un pirate bien connu ici, les gens m'appellent Bouboune. Bouboune the Pirate.");
		println("Et toi, comment t'appelles tu ?");
		while(equals(nomDuJoueur,"")){
			nomDuJoueur=readString();
			if (equals(nomDuJoueur,"")) {
				println("Je ne peux pas croire que vous n'avez aucun nom. Allez, ne soyez pas timide, dites-moi comment vous vous appelez !");
			}
		}
		clearScreen();
		cursor(0,0);
		discours(nomDuJoueur+" ? C'est un vrai nom de pirate ça ! Je suis sur que tu vas faire fortune en un rien de temps !");
		discours("Au fait, il faut que je te precise quelque chose. Ici et en mer, tout le monde parle anglais, et je ne serais pas toujours là pour t'aider : moi aussi j'ai mon business !");
		discours("Lorsque tu navigueras dans la mer, des pirates et des sirènes te testeront sur tes compétences en anglais. Si tu reponds bien aux énigmes, ils te donneront des trésors !");
		discours("Les pirates aiment les questions. C'est plus simple pour eux ! Ils te donneront 3 choix, tu repondras par un chiffre. Ils ne comprennent pas bien, si tu leur repond un mot, ils feront semblant d'avoir compris et te diront que tu as faux !");
		discours("Les sirène préfèrent les énigmes, elles s'ennuient souvent dans la mer, donc quand l'une d'elles te vois, elle veut jouer avec toi le plus longtemps possible.");
		discours("Allez ! Je te laisse faire ton propre business tout seul, en esperant que tu sauras repondre aux énigmes ! Good luck !");
		while(input!=1 && input!=2 && input!=3){
			print("Entrez le chiffre correspondant : \n1. Je n'ai jamais commencé l'anglais\n2. Je débute l'anglais\n3. Je me débrouille bien en anglais\n\nVotre niveau : ");
			while(length(inputS)!=1){
				inputS=readString();
			}
			input=charAt(inputS,0)-48;
			if (input!=1 && input!=2 && input!=3) {
				println("Ce n'est pas une entrée valide !\n");
				inputS="";
			}
		}
		niveau+=input;
	}

	void discours(String s){
		println(s+"\n\n< APPUYEZ SUR ENTRÉE POUR CONTINUER >");
		readString();
		clearScreen();
		cursor(0,0);
	}

	void direction(){
		int input=0;
		String inputS="";
		println("Vers ou souhaitez vous naviguer ?");
		println("Indiquez le chiffre correspondant :\n1. Nord\n2. Sud\n3. Est\n4. Ouest\n");
		while(input!=1 && input!=2 && input!=3 && input!=4){
			print("Direction choisie : ");
			while(length(inputS)!=1){
				inputS=readString();
			}
			input=charAt(inputS,0)-48;
			if (input!=1 && input!=2 && input!=3 && input!=4) {
				println("Ce n'est pas une entrée valide !");
				inputS="";
			}
		}
	}

	boolean fini(int score){
		if(score>=50){
			return true;
		} else {
			return false;
		}
	}

	void choixJeu(){
		CSVFile fileDialogues=loadCSV("Dialogues.csv");
		int rand=(int)(random()*3), randDial=(int)(random()*rowCount(fileDialogues));
		switch (rand) {
			case 0:
			delay(500);
			clearScreen();
			cursor(0,0);
			println("Vous tombez sur une sirène :");
			delay(1500);
			println(getCell(fileDialogues,randDial,1)+"\n");
			delay(1500);
			minijeu();
			break;
			case 1:
			delay(500);
			clearScreen();
			cursor(0,0);
			println("Vous tombez sur un pirate :");
			delay(1500);
			println(getCell(fileDialogues,randDial,0)+"\n");
			delay(1500);
			questions();
			break;
			case 2:
			delay(500);
			clearScreen();
			cursor(0,0);
			print("Il n'y a rien ici.");
			delay(500);
			break;
			default:
		}
	}

	void questions(){
		CSVFile fileQuestions=loadCSV("Question.csv");
		int idQuestion=randQuestion(fileQuestions);
		String rep="";
		boolean ok=false;
		println(getCell(fileQuestions,idQuestion,1));
		println(getCell(fileQuestions,idQuestion,2));
		print("Votre réponse : ");
		rep=readString();
		if (equals(getCell(fileQuestions,idQuestion,3),rep)) {
			ok=true;
			print("\nBravo ! C'est la bonne réponse !");
		} else {
			print("\nCe n'est pas la bonne réponse. La bonne réponse était :\""+getCell(fileQuestions,idQuestion,4)+"\" !");
		}
		victoire(ok);
	}

	int randQuestion(CSVFile fileQuestions){ 
		// Cette fonction prend l'id d'une question aléatoirement en prenant en compte le niveau du joueur
		int idDeb=-1, idFin=-1, cpt=0;
		String niveauString=""+niveau;
		while(idDeb==-1){
			if (equals(niveauString,getCell(fileQuestions,cpt,5))) {
				idDeb=cpt;
			}
			cpt+=1;
		}
		while(idFin==-1){
			if (!equals(niveauString,getCell(fileQuestions,cpt,5))) {
				idFin=cpt-1;
			}
			cpt+=1;
		}
		return (int)((random()*(idFin-idDeb+1))+idDeb);
	}

	void minijeu(){
		int rand=(int)(random()*4);
		boolean ok=false;
		switch(rand){
			case 0:
			ok=jeuDesChiffres();
			break;
			case 1:
			ok=jeuDesJours();
			break;
			case 2:
			ok=jeuDuPendu();
			break;
			case 3:
			ok=jeuDuMotus();
			break;
			default:
		}
		victoire(ok);
	}

	void victoire(boolean ok){
		if (ok) {
			score=score+chaine;
			chaine+=1;
			if (niveau!=9) {    
				niveau+=1;
			}
		} else {
			chaine=1;
			if (niveau!=0) {
				niveau-=1;
			}
		}
	}

//////////////////////////////////// Mini-jeux \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\


// Jeu des chiffres


	boolean jeuDesChiffres(){
		println("Ecris les chiffres que je prononce en toutes lettres, et tu seras recompensé.\n");
		String[] initial,reponses;
		if (niveau<3) {
			initial = new String[]{"One", "Two", "Three", "Four"};
			reponses = new String[]{"1", "2", "3", "4"};
		} else if (niveau>=3 && niveau<6) {
			initial = new String[]{"One", "Two", "Three", "Four", "Five", "Six"};
			reponses = new String[]{"1", "2", "3", "4", "5", "6"};
		} else {
			initial = new String[]{"One", "Two", "Three", "Four", "Five", "Six", "Seven", "Eight", "Nine", "Ten"};
			reponses = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
		}
		int rand = 0;
		String tmp, rep, tmpInt;
		boolean ok=true;
		println("Tapez les chiffres sous forme numérique (ex : One = 1) :");
		for(int i=0; i<length(initial); i++){
			rand = (int)(random()*(length(initial)));
			tmp=initial[i];
			initial[i] = initial[rand];
			initial[rand]=tmp;
			tmpInt=reponses[i];
			reponses[i] = reponses[rand];
			reponses[rand]=tmpInt;
		}
		for (int i=0; i<length(initial); i++) {
			print(initial[i]+" = ");
			rep=readString();
			if (!equals(rep,reponses[i])){
				ok=false;
			}
		}
		if (ok==true) {
			print("\nBravo, c'était la bonne réponse !");
		} else {
			println("\nCe n'était pas la bonne réponse. Les bonnes réponses étaient : ");
			for (int i=0; i<length(reponses); i++) {
				print(reponses[i]+" ");
			}
		}
		return ok;
	}


// Jeu des jours


	boolean jeuDesJours(){
		println("Remet les jours de la semaine dans le bon ordre !\n");
		String[] initial = new String[] {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
		String[] clone = new String[] {"monday", "tuesday", "wednesday", "thursday", "friday", "saturday", "sunday"};
		int rand = 0;
		String tmp = "";
		String rep = "";
		boolean ok = true;
		if (niveau<5) {
			print("Liste des jours : ");
			for (int i=0; i<length(initial); i++) {
				rand = (int)(random()*( 7 ));
				tmp=clone[i];
				clone[i] = clone[rand];
				clone[rand]=tmp;
			}
			for (int i=0; i<length(initial); i++) {
				print(clone[i]+" ");
			}
		}
		println();
		for (int i=0; i<length(initial); i++) {
			print((i+1)+"e jour : ");
			rep = readString();
			if (!equals(rep,initial[i])){
				ok = false;
			}
		}
		if (ok==true) {
			print("\nBravo, c'était la bonne réponse !");
		} else {
			println("\nCe n'était pas la bonne réponse. Les bonnes réponses étaient : ");
			for (int i=0; i<length(initial); i++) {
				print(initial[i]+" ");
			}
		}
		return ok;
	}


// Jeu du Pendu


	boolean jeuDuPendu() {
		println("Je pense à un animal, et tu dois le trouver en me donnant des lettres. Si la lettre que tu me donnes est dans le mot, je te le dirais.");
		String[] mots, traductions;
		int rand=(int)(random()*4);
		if (niveau<3) {
			mots = new String[]{"dog", "cow", "cat", "pig"};
			traductions = new String[]{"chien", "vache", "chat", "cochon"};
		} else if (niveau>=3 && niveau<6) {
			mots = new String[]{"sheep", "whale", "zebra", "horse"};
			traductions = new String[]{"mouton", "baleine", "zebre", "cheval"};
		} else {
			mots = new String[]{"rabbit", "monkey", "turtle", "spider"};
			traductions = new String[]{"lapin", "singe", "tortue", "araignée"};
		}
		String motAtrouver=mots[rand];
		String trad=traductions[rand];
		char c;
		int tentatives=5;
		boolean[] lettresTrouvees=initialiser(motAtrouver);
		while((!gagne(tentatives,lettresTrouvees)) && (tentatives!=0)){
			afficherEssai(tentatives,motAtrouver,lettresTrouvees);
			print("Entre un caractere : ");
			c=readChar();
			lettresTrouvees=majLettreTrouvees(c,lettresTrouvees,motAtrouver);
			tentatives=majTentatives(tentatives,c,motAtrouver,lettresTrouvees);
			if (tentatives==2) {
				println("INDICE : "+trad);
			}
		}
		if(gagne(tentatives,lettresTrouvees)) {
			print("Bravo, vous avez trouvé le mot : "+motAtrouver);
			return true;
		} else {
			println("Ce n'était pas la bonne réponse. Le mot à trouver était "+motAtrouver);
			return false;
		}
	}

	boolean[] initialiser(String mot) {
		boolean[] t=new boolean[length(mot)];
		for (int i=0; i<length(mot); i++) {
			t[i]=false;
		}
		return t;
	}

	boolean gagne(int tentatives, boolean[] lettresTrouvees) {
		boolean motTrouve=true;
		for (int i=0; i<length(lettresTrouvees); i++) {
			if (lettresTrouvees[i]==false) {
				motTrouve=false;
			} 
		}
		return motTrouve;
	}

	boolean[] majLettreTrouvees(char c, boolean[] lettresTrouvees, String motAtrouver) {
		for (int i=0; i<length(lettresTrouvees); i++) {
			if (charAt(motAtrouver,i)==c) {
				lettresTrouvees[i]=true;
			}
		}
		return lettresTrouvees;
	}

	int majTentatives(int tentatives, char c, String motAtrouver,boolean[] lettresTrouvees) {
		boolean res=false;
		for (int i=0; i<length(lettresTrouvees); i++) {
			if (charAt(motAtrouver,i)==c) {
				res=true;
			}
		}
		if (!res) {
			tentatives=tentatives-1;
		}
		return tentatives;
	}

	void afficherEssai(int tentatives, String motAtrouver, boolean[] lettresTrouvees) {
		println("\nMot à trouver : ");
		for (int i=0; i<length(lettresTrouvees); i++) {
			if (lettresTrouvees[i]) {
				print(charAt(motAtrouver,i));               
			} else {
				print("*");
			}
		}
		print("\n");
		println("Nombre restant de tentatives : "+tentatives);
	}


// Jeu du Motus


	boolean jeuDuMotus(){
		println("Trouve l'animal auquel je pense, et je te donnerais des indices pour te dire si tu te rapproche du mot caché\n");
		String[] mots, traductions;
		int rand=(int)(random()*4);
		if (niveau<3) {
			mots = new String[]{"dog", "cow", "cat", "pig"};
			traductions = new String[]{"chien", "vache", "chat", "cochon"};
		} else if (niveau>=3 && niveau<6) {
			mots = new String[]{"sheep", "whale", "zebra", "horse"};
			traductions = new String[]{"mouton", "baleine", "zebre", "cheval"};
		} else {
			mots = new String[]{"rabbit", "monkey", "turtle", "spider"};
			traductions = new String[]{"lapin", "singe", "tortue", "araignée"};
		}
		String motAtrouver=mots[rand];
		String trad=traductions[rand];
		String input;
		char[] trouve=new char[length(motAtrouver)];
		int essai=6;
		println("Un o signifie que la lettre est a la bonne place. Un * signifie que la lettre existe, mais mal positionnée.\n");
		while(!(gagne(trouve))&&(essai!=0)){
			println("\nIl vous reste "+essai+" essai(s).");
			input=inputMot(motAtrouver);
			trouve=joue(motAtrouver,input);
			affiche(trouve);
			essai-=1;
			if (essai==2) {
				println("INDICE : "+trad);
			}
		}
		if (gagne(trouve)) {
			println("\nBravo, vous avez trouvé le mot : "+motAtrouver);
			return true;
		} else {
			println("\nCe n'était pas la bonne réponse. Le mot à trouver était "+motAtrouver);
			return false;
		}
	}

	String inputMot(String motAtrouver){
		String res="";
		while(length(res)!=length(motAtrouver)){
			println("Entrez un mot : ( "+length(motAtrouver)+" lettres )");
			res=readString();
			if (length(res)!=length(motAtrouver)) {
				println("Vous avez entré un mot qui n'a pas le même nombre de lettres que le mot inconnu.");
			}
		}
		return res;
	}

	char[] joue(String motAtrouver, String input){
		char[] res=new char[length(motAtrouver)];
		boolean fini;
		for (int i=0; i<length(motAtrouver); i++) {
			fini=false;
			for (int j=0; j<length(motAtrouver); j++) {
				if ((charAt(input,i)==charAt(motAtrouver,i))&&!fini) {
					res[i]='o';
					fini=true;
				} else if ((charAt(input,i)==charAt(motAtrouver,j))&&!fini) {
					res[i]='*';
					fini=true;
				} else if (!fini) {
					res[i]='_';
				}
			}
		}
		return res;
	}

	void affiche(char[] trouve){
		for (int i=0; i<length(trouve); i++) {
			print(trouve[i]);
		}
		println();
	}

	boolean gagne(char[] trouve){
		boolean res=true;
		for (int i=0; i<length(trouve); i++) {
			if (trouve[i]!='o') {   
				res=false;
			}
		}
		return res;
	}


}
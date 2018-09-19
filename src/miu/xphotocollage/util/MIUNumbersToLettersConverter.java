package miu.xphotocollage.util;;

/**
 *
 * @author Ndadji Maxime
 */
public class MIUNumbersToLettersConverter {
    
    /**
     * Méthode principale appelée pour la traduction d'un nombre.
     * 
     * @param originalNumber
     * Le nombre à "traduire" qu'on convertit en chaine de caractère
     * @return String
     * La traduction du nombre en français
     */
    public static String readNumber(double originalNumber){
        // On convertit l'argument en chaine
        String number = originalNumber+"";
        
        // On récupère les parties entières et les parties décimales de notre nombre en le fractionnant
        String[] parts = number.split("\\.");
        
        // On traduit la partie décimale
        String decimal = readDecimalPart(parts[1]);
        
        // On traduit la partie entière
        String entier = readIntegerPart(parts[0]);
        
        /* Si la partie décimale vaut "zéro" on retourne la partie entière sinon on retourne "partie entière
         virgule partie décimale" */
        if(decimal.equalsIgnoreCase("zéro "))
            return entier.trim().toUpperCase();
        else
            return (entier+" virgule "+decimal).trim().toUpperCase();
    }
    
    /**
     * Cette méthode traduit une partie décimale. Elle prend en entrée une chaine (146) et elle retourne une
     * chaine correspondant à la traduction (un quatre six)
     * 
     * @param number
     * La partie décimale à lire
     * @return String
     * La traduction décimale de l'argument
     */
    private static String readDecimalPart(String number) {
        // Notre chaine résultat
        String result = "";
        String parts[] = {number};
        
        if(number.contains("E"))
            parts = number.split("E");
        
        // On parcoure la chaine d'entrée caractère après caratère et on les traduit individuellement
        int i = 0;
        for(i = 0; i < parts[0].length() && (parts[0].charAt(i)+"").equals("0"); i++)
            result += readDigits(parts[0].charAt(i)+"")+" ";
        if(i < parts[0].length())
            result += readIntegerPart(parts[0].substring(i));
        
        if(parts.length > 1)
            result += readDigits("E")+" "+readIntegerPart(parts[1]);
        
        return result;
    }
    
    /**
     * Cette méthode traduit un entier (25 en vingt et cinq par exemple)
     * 
     * @param number
     * Le nombre entier à lire
     * @return String
     * La traduction de l'argument
     */
    private static String readIntegerPart(String number) {
        //Nombre de chiffres du nombre
        int lenght = number.length();
        
        //Si c'est un seul chiffre
        if(lenght == 1)
            return readDigits(number);
        
        //Si c'est deux chiffres
        if(lenght == 2)
            return read2Digits(number);
        
        //Si c'est trois chiffres
        if(lenght == 3)
            return read3Digits(number);
        String part, rest;
        
        //Si c'est l'ordre des milliers
        if(lenght <= 6){
            //On divise en deux et on lit chaque partie
            part = number.substring(0, lenght - 3);
            rest = number.substring(lenght - 3);
            part = readIntegerPart(part);
            rest = readIntegerPart(rest);
            
            if(part.equalsIgnoreCase("un"))
                part = "";
            if(part.equalsIgnoreCase("zéro"))
                return rest;
            if(rest.equalsIgnoreCase("zéro"))
                rest = "";
            return part+" mille "+rest;
        }
        
        //Si c'est l'ordre du million
        if(lenght <= 9){
            part = number.substring(0, lenght - 6);
            rest = number.substring(lenght - 6);
            part = readIntegerPart(part);
            rest = readIntegerPart(rest);
   
            if(part.equalsIgnoreCase("zéro"))
                return rest;
            if(rest.equalsIgnoreCase("zéro"))
                rest = "";
            return part+" million "+rest;
        }
        
        //Si c'est de l'ordre du milliard et plus
        part = number.substring(0, lenght - 9);
        rest = number.substring(lenght - 9);
        part = readIntegerPart(part);
        rest = readIntegerPart(rest);

        if(part.equalsIgnoreCase("zéro"))
            return rest;
        if(rest.equalsIgnoreCase("zéro"))
            rest = "";
        return part+" milliard "+rest;
    }
    
    /**
     * Cette méthode traduit un chiffre
     * 
     * @param digit
     * Le chiffre à traduire
     * @return String
     * La traduction attendue
     */
    private static String readDigits(String digit) {
        int dig = 10;
        try{
            dig = Integer.parseInt(digit);
        }catch(Exception e){
            
        }
        switch(dig){
            case 0 : return "zéro";
            case 1 : return "un";
            case 2 : return "deux";
            case 3 : return "trois";
            case 4 : return "quatre";
            case 5 : return "cinq";
            case 6 : return "six";
            case 7 : return "sept";
            case 8 : return "huit";
            case 9 : return "neuf";
            default : {
                if(digit.equalsIgnoreCase("E"))
                    return "exposant";
                if(digit.equalsIgnoreCase("-"))
                    return "moins";
            }
        }
        return null;
    }
    
    /**
     * Cette méthode traduit un nombre de deux chiffres
     * 
     * @param number
     * Le nombre à traduire
     * @return String
     * La traduction attendue
     */
    private static String read2Digits(String number) {
        int digit_1 = Integer.parseInt(number.charAt(0)+"");
        int digit_2 = Integer.parseInt(number.charAt(1)+"");
        switch(digit_1){
            case 0 : return readDigits(digit_2+"");
            case 1 : {
                switch(digit_2){
                    case 0 : return "dix";
                    case 1 : return "onze";
                    case 2 : return "douze";
                    case 3 : return "treize";
                    case 4 : return "quatorze";
                    case 5 : return "quinze";
                    case 6 : return "seize";
                    default : return "dix-"+readDigits(digit_2+"");
                }
            }
            case 2 : {
                if(digit_2 == 0)
                    return "vingt";
                else
                    return "vingt "+readDigits(digit_2+"");
            }
            case 3 : {
                if(digit_2 == 0)
                    return "trente";
                else
                    return "trente "+readDigits(digit_2+"");
            }
            case 4 : {
                if(digit_2 == 0)
                    return "quarante";
                else
                    return "quarante "+readDigits(digit_2+"");
            }
            case 5 : {
                if(digit_2 == 0)
                    return "cinquante";
                else
                    return "cinquante "+readDigits(digit_2+"");
            }
            case 6 : {
                if(digit_2 == 0)
                    return "soixante";
                else
                    return "soixante "+readDigits(digit_2+"");
            }
            case 8 : {
                if(digit_2 == 0)
                    return "quatre-vingt";
                else
                    return "quatre-vingt "+readDigits(digit_2+"");
            }
            case 7 : return "soixante "+read2Digits("1"+digit_2);
            case 9 : return "quatre-vingt  "+read2Digits("1"+digit_2);
            default : return null;
        }
    }
    
    /**
     * Cette méthode traduit un nombre de rois chiffres
     * 
     * @param number
     * Le nombre à traduire
     * @return String
     * La traduction attendue
     */
    private static String read3Digits(String number) {
        int digit = Integer.parseInt(number.charAt(0)+"");
        String restOfChain = number.substring(1);
        switch(digit){
            case 0 : return read2Digits(restOfChain+"");
            case 1 : {
                String ch = read2Digits(restOfChain+"");
                if(ch.equalsIgnoreCase("zéro"))
                    ch = "";
                return "cent "+ch;
            }
            default : {
                String ch = read2Digits(restOfChain+"");
                if(ch.equalsIgnoreCase("zéro"))
                    ch = "";
                return readDigits(digit+"")+" cent "+ch;
            }
        }
    }
}



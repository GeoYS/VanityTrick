/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rtsgame.resources;

/**
 *
 * @author GeoYS_2
 */
public class Facts {
    public static String[] facts = {"The country with the largest number of WWII causalities was Russia, with over 21 million.",
    "For every five German soldiers who died in WWII, four of them died on the Eastern Front.",
    "Eighty percent of Soviet males born in 1923 didn’t survive WWII.",
    "The longest battle of WWII was the Battle of the Atlantic, which lasted from 1939-1945.",
    "Hobart's Funnies were a number of unusually modified tanks operated during the Second World War.",
    "In 1941, a private earned $21 a month. In 1942, a private earned $50 a month.",
    "The Germans used the first jet fighters in World War II, among them the Messerschmitt ME-262. However, they were developed too late to change the course of the war.",
    "During WWII, hamburgers in the U.S. were dubbed “Liberty Steaks” to avoid the German-sounding name.",
    "Prisoners of war in Russian camps experienced an 85% mortality rate."};
    
    public static String getRandomFact(){
        return facts[(int)(Math.random() * facts.length)];
    }
}

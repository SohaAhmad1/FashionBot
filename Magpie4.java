// class Main {
//   public static void main(String[] args) {
//     System.out.println("Hello world!");
//   }
// }


/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello, let's talk. What's the weather near you?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}
		else if (findKeyword(statement, "sunny") >= 0
				|| findKeyword(statement, "hot") >= 0
				|| findKeyword(statement, "dry") >= 0)
		{
			response = "Oh, you should wear a hat :). What is your favoirite item to wear during warmer seasons?";
		}

    else if (findKeyword(statement, "windy") >= 0
				|| findKeyword(statement, "rainy") >= 0
				|| findKeyword(statement, "cold") >= 0)
		{
			response = "Dang, well be sure to keep cozy. Do you enjoy the colder weather?";
		}

    else if (findKeyword(statement, "jacket") >= 0
				|| findKeyword(statement, "hat") >= 0
				|| findKeyword(statement, "coat") >= 0
        || findKeyword(statement, "clothes") >= 0)
		{
			response = "Oh, what is your favorite fashion trend this season?";
		}

    else if (findKeyword(statement, "shoes") >= 0
        || findKeyword(statement, "sneakers") >= 0
				|| findKeyword(statement, "sweaters") >= 0
        || findKeyword(statement, "hoodies") >= 0
				|| findKeyword(statement, "sweatshirt") >= 0
        || findKeyword(statement, "sweatpants") >= 0)
		{
			response = "I love those too! What is your favorite color to wear them with?";
		}

    else if (findKeyword(statement, "color") >= 0
        || findKeyword(statement, "pink") >= 0
				|| findKeyword(statement, "purple") >= 0
        || findKeyword(statement, "red") >= 0
				|| findKeyword(statement, "green") >= 0
        || findKeyword(statement, "blue") >= 0
        || findKeyword(statement, "black") >= 0
        || findKeyword(statement, "yellow") >= 0
        || findKeyword(statement, "orange") >= 0
        || findKeyword(statement, "grey") >= 0)
		{
			response = "Cool! My favorite color is teal. What are you wearing right now?";
		}

    else if (findKeyword(statement, "yes") >= 0)
		{
			response = YesResponce(statement);
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

    else if (findKeyword(statement, "I need to", 0) >= 0)
		{
			response = transformINeedToStatement(statement);
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				response = getRandomResponse();
			}
		}
		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "Why do you want " + restOfStatement + "?";
	}

  private String transformINeedToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I need to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

  //indicates end of conversation, yes responce prompts a new question related to our topic
  private String YesResponce(String statement)
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting..What is your favorite clothing item at the moment?";
		}
		else if (whichResponse == 1)
		{
			response = "Cool! What is your favorite clothing brand?";
		}
		else if (whichResponse == 2)
		{
			response = "Well, what are your thoughts on 2000's fashion trends coming back?";
		}
		else if (whichResponse == 3)
		{
			response = getRandomFact();
    }

		return response;
	}
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	

  // pick random fashion fact if conversation seems slow
	private String getRandomFact()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Did you know that T-shirts are one of the most popular clothing items, with more than 2 billion of them sold each year.";
		}
		else if (whichResponse == 1)
		{
			response = "Let me tell you something interesting. I heard that before becoming highly publicized and popular events that attract the rich and famous, Fashion Weeks were only trade events meant to bring together manufacturers and buyers.";
		}
		else if (whichResponse == 2)
		{
			response = "Want to know something cool I just learned, Sandals are the earliest known shoes, dating back to roughly 7,000 B.C.";
		}
		else if (whichResponse == 3)
		{
			response = "Hears a cool fashion fact:  Designers used little dolls instead of human models in 15th century to showcase their creations to clients.";
    }

		return response;
	}

  
	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 4;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "Hmmm.";
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse == 3)
		{
			response = "You don't say.";
    }

		return response;
	}

}
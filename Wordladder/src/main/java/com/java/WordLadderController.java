package com.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
public class WordLadderController 
{
	@RequestMapping(value =  "/wordladder")
	public static String main(@RequestParam("word1") String beginWord, @RequestParam("word2") String endWord)
	{
		Logger logger = LoggerFactory.getLogger(WordLadderController.class);
		List<String> wordList= new ArrayList<>();
		try 
		{
            String pathname = "src\\main\\resources\\static\\EnglishWords.txt";
            File filename = new File(pathname);
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));
            BufferedReader br = new BufferedReader(reader);
            String line = "";
            line = br.readLine();
            while (line != null)
            { 
                line = br.readLine();
                wordList.add(line);
            }  
		}
		catch (Exception e) 
		{  
            e.printStackTrace();  
		}
		

		
		/*Scanner sc=new Scanner(System.in);
		System.out.println("Word#1(or Enter to quit):");
		String Word1=sc.nextLine();
		if(Word1.length()==0)
		{
			System.out.println("Have a nice day!");
			System.exit(0);
		}
		String beginWord=Word1;
		System.out.println("Word#2(or Enter to quit):");
		String Word2=sc.nextLine();
		if(Word2.length()==0)
		{
			System.out.println("Have a nice day!");
			System.exit(0);
		}
		String endWord=Word2;
		
		if(beginWord.length()!=endWord.length())
		{
			System.out.println("The two words must be the same length.");
			System.exit(0);
		}
		if(endWord.equals(beginWord))
		{
			System.out.println("The two words must be different.");
			System.exit(0);
		}
		*/
		
		
		
		Queue<Stack> ladder = new LinkedList<Stack>();
		Set<String> wordSet = new HashSet<>(wordList);
        Set<String> visited = new HashSet<>();
        Stack<String> temp = new Stack<String>();
        Stack<String> beginStack= new Stack<String>();
        String result;
        beginStack.push(beginWord);
		ladder.add(beginStack);
        while(!ladder.isEmpty())
        {
        	while(!temp.empty())
        	{
        		temp.pop();
        	}
        	temp=ladder.peek();
        	ladder.poll();
        	
        	for(int i=0;i<temp.peek().length();i++)
        	{
        		char[] chars = temp.peek().toCharArray();
        		for(char c='a';c<='z';c++)
        		{
        			if (chars[i]==(char)c)
        			{
        				continue;
        			}
        			chars[i] = (char)c;
        			String newWord=new String(chars);
        			if(visited.contains(newWord))
        			{
        				continue;
        			}
        			if(newWord.equals(endWord))
        			{
        				result="A ladder from "+endWord+" back to "+beginWord+": "+endWord+" ";
        				System.out.print(endWord + " ");
        				while(!temp.empty())
        				{
        					result+=temp.peek()+ " ";
        					temp.pop();
        				}
        				logger.debug("Success run: "+result);
        				return result;
        			}
        			if(wordSet.contains(newWord))
        			{
        				temp.push(newWord);
        				Stack<String> tt = (Stack<String>)temp.clone();
        				ladder.add(tt);
        				temp.pop();
        				visited.add(newWord);
        			}
        		}      	
            }
        }
        logger.debug("No wordladder found from "+endWord+" back to "+beginWord+".");
        return "No wordladder found from "+endWord+" back to "+beginWord+".";
	}
}

package provided;

/**
 * This class is responsible for paring Jott Tokens
 * into a Jott parse tree.
 *
 * @author
 */

import java.util.ArrayList;

import grammar.ProgramNode;

public class JottParser { // calls converttojott on programNode

    /**
     * Parses an ArrayList of Jott tokens into a Jott Parse Tree.
     * @param tokens the ArrayList of Jott tokens to parse
     * @return the root of the Jott Parse Tree represented by the tokens.
     *         or null upon an error in parsing.
     *
     */

    static class programNode{
      String value;
      ArrayList<programNode> children;

      programNode(String value){
        this.value = value;
        this.children = new ArrayList<>();
      }
      void addChildren(programNode child){
        children.add(child);
      }
    }

    private programNode root;

    public static JottTree parse(ArrayList<Token> tokens) throws Exception{
      try{
        ProgramNode rootnode = ProgramNode.parseProgramNode(tokens); 
        return rootnode;
      }
      catch (Exception e) {
        return null;
      }
    }


    /*
    public static JottParser parse(ArrayList<Token> tokens){
      // return the root of the tree represented by those tokens
      // funcdefnode is the one that calls its children
      // programNode( top of tree, first called), all the way down to MathOp(the bottom of the tree)
      // substitute treenode for programNode
      JottParser jp = new JottParser();
      if (tokens == null || tokens.isEmpty()){
        System.err.println("Error: Token list is empty or null");
        return null;
      }

      try {
        jp.root = new programNode("root");
        for (Token token: tokens){
          programNode childNode = new programNode(token.getToken());
          jp.root.addChildren(childNode);
        }
      }catch (Exception e){
        System.out.println("Error creating the tree: " + e.getMessage());
        return null;
      }
      return jp;
    }

    public static void main(String[] args){
      ArrayList<String>tokens = new ArrayList<>();
      tokens.add("sample_token1");

      JottParser jp = JottParser.parse(tokens);
      if (jp != null){
        System.out.println("tree created successfully with root value: "  + jp);
      }

    }
      */
}

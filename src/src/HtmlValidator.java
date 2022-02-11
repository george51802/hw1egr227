package src;
import java.util.*;

/**
 * HTML Validator by Eric Schmidt, Nathan, and George Ghandour.
 * February 3, 2022
 *
 * Program: This program uses Java's Stack and Queue data structures to validate, add, remove, get,
 * and print, Html code from an input .html source.
 */
public class HtmlValidator {

    private static final String INDENTATION_MARKER = "    ";

    private Queue<HtmlTag> tags;


    public HtmlValidator() {
        tags = new LinkedList<>();
    }

    //This method creates an HtmlValidator with the content of the HTML tags
    public HtmlValidator(Queue<HtmlTag> tags) {
        if (tags == null) {
            throw new IllegalArgumentException("Initial tags cannot be null.");
        }
        // Create a deep copy of the input queue:
        this.tags = new LinkedList<>(tags);
    }


    //This method adds a tag to the end of the HtmlValidator Queue
    public void addTag(HtmlTag tag) {
        if (tag == null) {
            throw new IllegalArgumentException("Tags cannot be null.");
        }
        tags.add(tag);
    }

    //This method returns a deep copy of the tags in the HtmlValidator
    public Queue<HtmlTag> getTags() {
        return new LinkedList<>(tags);
    }

    //This method removes all HTML tags that match the given element parameter
    public void removeAll(String element) {
        if (element == null) throw new IllegalArgumentException("Element passed cannot be null.");
        tags.removeIf(tag -> tag.getElement().equalsIgnoreCase(element));
    }

    //This method prints formatted HTML based on the content of the HtmlValidator using the tags Queue and Stack Data Structures
    public void validate() {
        Stack<HtmlTag> openTags = new Stack<>();
        int indentNum = 0;
        for (int i = 0; i < tags.size(); i++) {
            // Put each element <title> from the tags queue into a stack and increase indentation.
            // Once you detect a closing tag </title> decrease indentation and pop the top tag in openTags.
            HtmlTag tag = tags.remove();
            if (!tag.isOpenTag()) {
                if (!openTags.isEmpty() && tag.matches(openTags.peek())) {
                    indentNum--;
                    printWithIndentation(tag, indentNum);
                    openTags.pop();
                } else {
                    System.out.println("ERROR unexpected tag: " + tag);
                }
            } else {
                printWithIndentation(tag, indentNum);
                if (!tag.isSelfClosing()) {
                    indentNum++;
                    openTags.add(tag);
                }
            }
            tags.add(tag);
        }
        //Unclosed tag error
        while (!openTags.isEmpty()) {
            HtmlTag tag = openTags.pop();
            System.out.println("ERROR unclosed tag: " + tag);
        }
    }

    private static void printWithIndentation (HtmlTag tag,int indentNum){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indentNum; i++) {
            sb.append(INDENTATION_MARKER);
        }
        sb.append(tag.toString());
        System.out.println(sb);

    }
}
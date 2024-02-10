import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ccdr.labyrinth.menu.tree.MenuButtonElement;
import com.ccdr.labyrinth.menu.tree.MenuChoiceElement;
import com.ccdr.labyrinth.menu.tree.MenuElement;
import com.ccdr.labyrinth.menu.tree.MenuListElement;
import com.ccdr.labyrinth.menu.tree.MenuTextElement;

/**
 * Class that contains all test cases for MenuElements.
 */
public final class MenuElementTest {

    private MenuElement tree;
    private boolean signal = false;

    @BeforeEach
    void setupMenuTree() {
        tree = new MenuListElement("Root",
            new MenuTextElement("Text element", "Description for text element"),
            new MenuChoiceElement<>("Choice box", List.of("a", "b", "c", "d")),
            new MenuListElement("InnerList",
                new MenuTextElement("Clickable Text", null),
                new MenuButtonElement("Clickable Button", () -> System.out.println("clicked"))),
            new MenuButtonElement("Fake Play", () -> signal = true)
        );
        signal = false;
    }

    @Test
    void movementUp() {
        //menu list should not wrap around when scrolling
        MenuListElement root = (MenuListElement) tree;
        for (int i = 0; i < 10; i++) {
            tree.up();
        }
        assertEquals(0, root.getIndex());
    }

    @Test
    void movementDown() {
        //menu list should not wrap around when scrolling
        MenuListElement root = (MenuListElement) tree;
        for (int i = 0; i < 10; i++) {
            tree.down();
        }
        assertEquals(3, root.getIndex());
    }

    @Test
    void movementEnterText() {
        //text element should return back to the parent once selected
        assertEquals(tree, tree.nextState().nextState());
    }

    @Test
    void movementEnterChoice() {
        tree.down();
        MenuElement elm = tree.nextState();
        if (elm instanceof MenuChoiceElement<?>) {
            MenuChoiceElement<?> choice = ((MenuChoiceElement<?>) elm);
            //pass only if the chosen object is 'c'
            choice.action(obj -> signal = obj.equals("c"));
            choice.down();
            choice.down();
            MenuElement shouldBeRoot = choice.nextState();
            assertTrue(signal);
            assertEquals(tree, shouldBeRoot);
        } else {
            fail("Wrong object type selected in menu");
        }
    }

    @Test
    void movementEnterList() {
        tree.down();
        tree.down();
        //if it's a list, then this should not be the same as root by definition
        assertNotEquals(tree, tree.nextState().nextState());
        assertEquals(tree.nextState(), tree.nextState().nextState().nextState());
    }

    @Test
    void movementEnterButton() {
        tree.down();
        tree.down();
        tree.down();
        MenuElement button = tree.nextState();
        //if it's a button, then these should throw an exception
        assertThrowsExactly(IllegalStateException.class, () -> {
            button.nextState();
        });

        assertThrowsExactly(IllegalStateException.class, () -> {
            button.up();
        });

        assertThrowsExactly(IllegalStateException.class, () -> {
            button.down();
        });

        //this must be fine to call on the button object
        button.immediate();
        assertTrue(signal);
    }


}

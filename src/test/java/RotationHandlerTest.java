import com.algtransformer.RotationHandler;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RotationHandlerTest {

    private final RotationHandler rh = new RotationHandler();

    @Test
    void setRotationFlagTest() {
        char x = 'x';

        rh.setCurrentRotation(rh.getRotationDegree(String.valueOf(x), -1));
        assertEquals(rh.getCurrentRotation(), "x'");

        rh.setCurrentRotation(rh.getRotationDegree(String.valueOf(x), 2));
        assertEquals(rh.getCurrentRotation(), "x2");

        rh.setCurrentRotation(rh.getRotationDegree(String.valueOf(x), 1));
        assertEquals(rh.getCurrentRotation(), "x");

    }

    @Test
    void affectCurrentRotationTest() {

        RotationHandler rh = new RotationHandler();
        assertEquals(rh.getCurrentRotation(), "");

        rh.affectCurrentRotation("x");
        assertEquals(rh.getCurrentRotation(), "x");

        rh.affectCurrentRotation("x"); //x2
        assertEquals(rh.getCurrentRotation(), "x2");

        rh.affectCurrentRotation("y2"); //z2
        assertEquals(rh.getCurrentRotation(), "z2");

        rh.affectCurrentRotation("z'"); //z
        assertEquals(rh.getCurrentRotation(), "z");

        rh.affectCurrentRotation("z'"); //null
        assertEquals(rh.getCurrentRotation(), "");
        rh.affectCurrentRotation("x'"); //x'
        assertEquals(rh.getCurrentRotation(), "x'");

        rh.affectCurrentRotation("y"); //x' y
        assertEquals(rh.getCurrentRotation(), "x' y");

        rh.affectCurrentRotation("y"); //x' y2
        assertEquals(rh.getCurrentRotation(), "x' y2");

        rh.affectCurrentRotation("y2"); //x'
        assertEquals(rh.getCurrentRotation(), "x'");

        rh.affectCurrentRotation("x'"); //x2
        assertEquals(rh.getCurrentRotation(), "x2");

        rh.affectCurrentRotation("y2"); //z2
        assertEquals(rh.getCurrentRotation(), "z2");

        RotationHandler r2 = new RotationHandler("x y2");
        assertEquals(r2.getCurrentRotation(), "x y2");

        r2.affectCurrentRotation("x2");
        assertEquals(r2.getCurrentRotation(), "x z2");

        r2.affectCurrentRotation("y2");
        assertEquals(r2.getCurrentRotation(), "x'");

        RotationHandler r3 = new RotationHandler("x2 y");
        assertEquals(r3.getCurrentRotation(), "x2 y");

        r3.affectCurrentRotation("z'");
        assertEquals(r3.getCurrentRotation(), "x y");

        r3.affectCurrentRotation("z2");
        assertEquals(r3.getCurrentRotation(), "x' y");

        RotationHandler r4 = new RotationHandler("x' y2");
        assertEquals(r4.getCurrentRotation(), "x' y2");

        r4.affectCurrentRotation("z");
        assertEquals(r4.getCurrentRotation(), "x z'");

        r4.affectCurrentRotation("y'");
        assertEquals(r4.getCurrentRotation(), "z'");

        RotationHandler r5 = new RotationHandler("x' y");
        assertEquals(r5.getCurrentRotation(), "x' y");

        r5.affectCurrentRotation("x");
        assertEquals(r5.getCurrentRotation(), "z'");

        r5.affectCurrentRotation("y'");
        assertEquals(r5.getCurrentRotation(), "z' y'");

        r5.affectCurrentRotation("z'");
        assertEquals(r5.getCurrentRotation(), "z2 x");

        RotationHandler r6 = new RotationHandler("x y2");
        assertEquals(r6.getCurrentRotation(), "x y2");

        r6.affectCurrentRotation("x");
        assertEquals(r6.getCurrentRotation(), "y2");

        RotationHandler r7 = new RotationHandler("x y2");
        assertEquals(r7.getCurrentRotation(), "x y2");

        r7.affectCurrentRotation("x'");
        assertEquals(r7.getCurrentRotation(), "z2");

        RotationHandler r8 = new RotationHandler("y z'");
        assertEquals(r8.getCurrentRotation(), "y z'");

        r8.affectCurrentRotation("y2");
        assertEquals(r8.getCurrentRotation(), "y' z");

        RotationHandler r9 = new RotationHandler("y2 z'");
        assertEquals(r9.getCurrentRotation(), "y2 z'");

        r9.affectCurrentRotation("y2");
        assertEquals(r9.getCurrentRotation(), "z");

        RotationHandler r10 = new RotationHandler("y' z2");
        assertEquals(r10.getCurrentRotation(), "y' z2");

        r10.affectCurrentRotation("x");
        assertEquals(r10.getCurrentRotation(), "y x'");
    }
}

package net.worlder;

import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWKeyCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

/**
 * LWJGL test.
 * @author shzbj
 */
public class Main {
    private final GLFWErrorCallback errorCallback =
            GLFWErrorCallback.createPrint(System.err);
    private final GLFWKeyCallback keyCallback = new GLFWKeyCallback() {
        @Override
        public void invoke(long window, int key, int scancode, int action, int mods) {
            if (key == GLFW_KEY_ESCAPE && action == GLFW_PRESS) {
                glfwSetWindowShouldClose(window, true);
            }
        }
    };
    private final IntBuffer width = memAllocInt(1);
    private final IntBuffer height = memAllocInt(1);
    private long window;

    public static void main(String[] args) {
        Main main = new Main();
        main.init();
        main.run();
    }

    private void init() {
        glfwSetErrorCallback(errorCallback);
        if (!glfwInit()) {
            throw new RuntimeException("Init GLFW failed");
        }
        window = glfwCreateWindow(600, 600, "Worlder", NULL, NULL);
        if (window == NULL) {
            glfwTerminate();
            throw new RuntimeException("Create window failed");
        }
    }

    private void run() {
        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        glfwSetWindowPos(window, (vidMode.width() - 640) / 2, (vidMode.height() - 480) / 2);
        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwSwapInterval(1);
        glfwSetKeyCallback(window, keyCallback);

        while (!glfwWindowShouldClose(window)) {
            float ratio;
            
            glfwGetFramebufferSize(window, width, height);
            ratio = width.get() / (float) height.get();

            width.rewind();
            height.rewind();

            glViewport(0, 0, width.get(), height.get());
            glClear(GL_COLOR_BUFFER_BIT);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            glOrtho(-ratio, ratio, -1f, 1f, 1f, -1f);
            glMatrixMode(GL_MODELVIEW);
            
            glLoadIdentity();
            glRotatef((float) glfwGetTime() * 50f, 0f, 0f, 1f);

            glBegin(GL_TRIANGLES);
            glColor3f(1f, 0f, 0f);
            glVertex3f(-0.6f, -0.4f, 0f);
            glColor3f(0f, 1f, 0f);
            glVertex3f(0.6f, -0.4f, 0f);
            glColor3f(0f, 0f, 1f);
            glVertex3f(0f, 0.6f, 0f);
            glEnd();

            
            glfwSwapBuffers(window);
            glfwPollEvents();
            
            width.flip();
            height.flip();

            memFree(width);
            memFree(height);
            
            glfwDestroyWindow(window);
            keyCallback.free();

            glfwTerminate();
            errorCallback.free();
        }
        destroy();
    }

    private void destroy() {
        memFree(width);
        memFree(height);

        glfwDestroyWindow(window);
        keyCallback.free();
        glfwTerminate();
        errorCallback.free();
    }
}

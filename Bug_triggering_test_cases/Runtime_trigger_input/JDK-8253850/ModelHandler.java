

package com.apollo.timewreak.engine;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL46.*;

public class ModelHandler {

    private int drawCount;
    private int vID;

    public ModelHandler(float[] vertices){
        drawCount = vertices.length / 3;

        FloatBuffer buffer = BufferUtils.createFloatBuffer(vertices.length);
        buffer.put(vertices);
        buffer.flip();
        vID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vID);
        glBufferData(GL_ARRAY_BUFFER, buffer,GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void render(){
        glEnable(GL_VERTEX_ARRAY);
        glBindBuffer(GL_ARRAY_BUFFER, vID);
        glVertexPointer(3, GL_FLAT, 0, 0);
        glDrawArrays(GL_TRIANGLES, 0, drawCount);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
        glDisable(GL_VERTEX_ARRAY);
    }

}


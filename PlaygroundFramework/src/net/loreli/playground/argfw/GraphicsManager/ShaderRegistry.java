package net.loreli.playground.argfw.GraphicsManager;


public class ShaderRegistry {
	
	/* Simple, also default */
	
	static String m_sVsSimple =
			"attribute vec4 aPosition;" +
			"uniform mat4 uProjMat;" +
			"uniform mat4 uViewMat;" +
			"uniform mat4 uModelMat;" +
			"void main() {" +
			"	gl_Position = uProjMat * (uViewMat * (uModelMat * aPosition));" +
			"}";
	
	static String m_sFsSimple =
			"precision mediump float;" +
			"void main() {" +
			"	gl_FragColor = vec4(0.8, 0.8, 0.8, 1);" + 
			"}";
	
	public static ShaderProgram CreateSimple() {
		ShaderProgram prog = new ShaderProgram();
		
		prog.AttachShader(new VertexShader(m_sVsSimple));
		prog.AttachShader(new FragmentShader(m_sFsSimple));

		prog.NewUniform("uProjMat");
		prog.NewUniform("uViewMat");
		prog.NewUniform("uModelMat");
		
		return prog;
	}
	
	/* Simple + Texture */
	
	static String m_sVsSimpleTextures =
			"attribute vec4 aPosition;" +
			"attribute vec2 aTexCoord;" +
			"uniform mat4 uProjMat;" +
			"uniform mat4 uViewMat;" +
			"uniform mat4 uModelMat;" +
			"varying vec2 vTexCoord;" +
			"void main() {" +
			"	vTexCoord = aTexCoord;" +
			"	gl_Position = uProjMat * (uViewMat * (uModelMat * aPosition));" +
			"}";
	
	static String m_sFsSimpleTextures =
			"precision mediump float;" +
			"uniform sampler2D uTexture;" +
			"varying vec2 vTexCoord;" +
			"void main() {" +
			"	gl_FragColor = texture2D(uTexture, vTexCoord);" +
			"}";
	
	public static ShaderProgram CreateSimpleTextured() {
		ShaderProgram prog = new ShaderProgram();
		
		prog.AttachShader(new VertexShader(m_sVsSimpleTextures));
		prog.AttachShader(new FragmentShader(m_sFsSimpleTextures));

		prog.NewUniform("uProjMat");
		prog.NewUniform("uViewMat");
		prog.NewUniform("uModelMat");
		prog.NewUniform("uTexture");
		
		return prog;
	}
	
	/* Phong */
	
	static String m_sVsPhong =
			"attribute vec4 aPosition;" +
			"attribute vec3 aNormal;" +
			"uniform mat4 uProjMat;" +
			"uniform mat4 uViewMat;" +
			"uniform mat4 uModelMat;" +
			"varying vec3 vPosition;" +
			"varying vec3 vNormal;" +
			"void main() {" +
			"	vNormal = (uViewMat * uModelMat * vec4(aNormal, 0)).xyz;" +
			"   vPosition = (uViewMat * (uModelMat * aPosition)).xyz;" +
			"	gl_Position = uProjMat * (uViewMat * (uModelMat * aPosition));" +
			"}";
	
	static String m_sFsPhong =
			"precision mediump float;" +
			"varying vec3 vPosition;" +
			"varying vec3 vNormal;" +
			"void main() {" +
			"   float specularity = 30.0;" +
			"   vec3 v3LightColor = vec3(0.8,0.8,0.8);" +
			"   float NdotL = dot(normalize(vec3(1,1,1)), normalize(vNormal));" +
			"   vec3 ambient = 0.05 * v3LightColor;" +
			"   vec3 diffuse = NdotL * v3LightColor;" +
			"   vec3 v3Reflection = reflect(normalize(vec3(1,1,1)), normalize(vNormal));" +
			"   float VdotR = dot(normalize(vPosition), v3Reflection);" +
			"   vec3 specular = pow(clamp(VdotR, 0.0, 1.0), specularity) * v3LightColor;" +
//			"   vec3 specular = pow(0.3, specularity) * v3LightColor;" +
			"	gl_FragColor = vec4(clamp(ambient + diffuse + specular, 0.0,1.0), 1.0);" +
//			"	gl_FragColor = vec4(clamp(ambient + diffuse, 0.0,1.0), 1.0);" +
			"}";
	
	public static ShaderProgram CreatePhong() {
		ShaderProgram prog = new ShaderProgram();
		
		prog.AttachShader(new VertexShader(m_sVsPhong));
		prog.AttachShader(new FragmentShader(m_sFsPhong));

		prog.NewUniform("uProjMat");
		prog.NewUniform("uViewMat");
		prog.NewUniform("uModelMat");
		
		return prog;
	}
	
}

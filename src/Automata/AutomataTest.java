/**
 * AutoAnalyzer (COMP 2013-2014)
 *
 * Eduardo Fernandes (200803951)
 * João Correia      (201208114)
 * José Pinto        (201203811)
 *
 * JUnit 4 test cases
 */

package Automata;

import org.junit.Test;

import java.io.*;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class AutomataTest {

    @Test
    public void testParser() throws Exception {

		String file_content = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"C[shape=doublecircle]\r\n" +
				"\"\"->A\r\n" +
				"A->b[label=a]\r\n" +
				"b->A[label=b]\r\n" +
				"b->C[label=a]\r\n" +
				"C->C[label=b]\r\n" +
				"}";

		Automata test_automata = new Automata(file_content);
		String output_file = test_automata.convertToDotty();

		File file = new File("./junit/junit_testParser.gv");

		file.createNewFile();

		PrintWriter writer=null;
		try {
			writer = new PrintWriter(file.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		}
		writer.println(output_file);
		writer.close();


		assertEquals("Converted automata must equal the initial file content: ", file_content, output_file);
	}

    @Test
    public void testCartesianProduct() {

		String file_content1 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"s1[shape=doublecircle]\r\n" +
				"\"\"->s0\r\n" +
				"s0->s1[label=a]\r\n" +
				"s0->s0[label=b]\r\n" +
				"s0->s2[label=c]\r\n" +
				"s1->s1[label=a]\r\n" +
				"s1->s1[label=b]\r\n" +
				"s1->s2[label=c]\r\n" +
				"s2->s2[label=a]\r\n" +
				"s2->s2[label=b]\r\n" +
				"s2->s2[label=c]\r\n" +
				"}";

		String file_content2 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"q1[shape=doublecircle]\r\n" +
				"\"\"->q0\r\n" +
				"q0->q0[label=a]\r\n" +
				"q0->q1[label=b]\r\n" +
				"q0->q0[label=c]\r\n" +
				"q1->q0[label=a]\r\n" +
				"q1->q1[label=b]\r\n" +
				"q1->q0[label=c]\r\n" +
				"}";

		String result = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"\"\"->s0_q0\r\n" +
				"s0_q1->s1_q0[label=a]\r\n" +
				"s0_q1->s0_q1[label=b]\r\n" +
				"s0_q1->s2_q0[label=c]\r\n" +
				"s0_q0->s1_q0[label=a]\r\n" +
				"s0_q0->s0_q1[label=b]\r\n" +
				"s0_q0->s2_q0[label=c]\r\n" +
				"s2_q1->s2_q0[label=a]\r\n" +
				"s2_q1->s2_q1[label=b]\r\n" +
				"s2_q1->s2_q0[label=c]\r\n" +
				"s2_q0->s2_q0[label=a]\r\n" +
				"s2_q0->s2_q1[label=b]\r\n" +
				"s2_q0->s2_q0[label=c]\r\n" +
				"s1_q1->s1_q0[label=a]\r\n" +
				"s1_q1->s1_q1[label=b]\r\n" +
				"s1_q1->s2_q0[label=c]\r\n" +
				"s1_q0->s1_q0[label=a]\r\n" +
				"s1_q0->s1_q1[label=b]\r\n" +
				"s1_q0->s2_q0[label=c]\r\n" +
				"}";

		Automata test_automata1 = new Automata(file_content1);
		Automata test_automata2 = new Automata(file_content2);
		Automata test_automata3 = test_automata1.getCartesianProduct(test_automata2);

		String output_file = test_automata3.convertToDotty();

		File file1 = new File("./junit/junit_testCartesianProduct.gv");

		PrintWriter writer=null;

		try {
			file1.createNewFile();
			writer = new PrintWriter(file1.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println(output_file);
		writer.close();


		assertEquals("Cartesian product automata must equal the actual initial cartesian product file content: ",output_file, result);
    }

    @Test
    public void testIntersection(){
		String file_content1 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"A[shape=doublecircle]\r\n" +
				"\"\"->A\r\n" +
				"A->A[label=b]\r\n" +
				"A->B[label=a]\r\n" +
				"B->A[label=a]\r\n" +
				"B->B[label=b]\r\n" +
				"}";

		String file_content2 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"X[shape=doublecircle]\r\n" +
				"\"\"->X\r\n" +
				"X->Y[label=a]\r\n" +
				"X->X[label=b]\r\n" +
				"Y->X[label=b]\r\n" +
				"Y->Z[label=a]\r\n" +
				"Z->Z[label=a]\r\n" +
				"Z->Z[label=b]\r\n" +
				"}";

		String result = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"A_X[shape=doublecircle]\r\n" +
				"\"\"->A_X\r\n" +
				"A_Y->A_X[label=b]\r\n" +
				"A_Y->B_Z[label=a]\r\n" +
				"A_X->A_X[label=b]\r\n" +
				"A_X->B_Y[label=a]\r\n" +
				"A_Z->A_Z[label=b]\r\n" +
				"A_Z->B_Z[label=a]\r\n" +
				"B_Y->A_Z[label=a]\r\n" +
				"B_Y->B_X[label=b]\r\n" +
				"B_X->A_Y[label=a]\r\n" +
				"B_X->B_X[label=b]\r\n" +
				"B_Z->A_Z[label=a]\r\n" +
				"B_Z->B_Z[label=b]\r\n" +
				"}";

		Automata test_automata1 = new Automata(file_content1);
		Automata test_automata2 = new Automata(file_content2);
		Automata test_automata3 = test_automata1.getIntersection(test_automata2);

		String output_file = test_automata3.convertToDotty();

		File file1 = new File("./junit/junit_testIntersection.gv");

		PrintWriter writer=null;

		try {
			file1.createNewFile();
			writer = new PrintWriter(file1.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println(output_file);
		writer.close();


		assertEquals("Intersection automata must equal the actual initial intersection file content: ",output_file, result);
    }

    @Test
    public void testComplement(){
		String file_content1 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"s1[shape=doublecircle]\r\n" +
				"\"\"->s0\r\n" +
				"s0->s1[label=a]\r\n" +
				"s0->s0[label=b]\r\n" +
				"s0->s2[label=c]\r\n" +
				"s1->s1[label=a]\r\n" +
				"s1->s1[label=b]\r\n" +
				"s1->s2[label=c]\r\n" +
				"s2->s2[label=a]\r\n" +
				"s2->s2[label=b]\r\n" +
				"s2->s2[label=c]\r\n" +
				"}";

		String result = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"s0[shape=doublecircle]\r\n" +
				"s2[shape=doublecircle]\r\n" +
				"\"\"->s0\r\n" +
				"s0->s1[label=a]\r\n" +
				"s0->s0[label=b]\r\n" +
				"s0->s2[label=c]\r\n" +
				"s1->s1[label=a]\r\n" +
				"s1->s1[label=b]\r\n" +
				"s1->s2[label=c]\r\n" +
				"s2->s2[label=a]\r\n" +
				"s2->s2[label=b]\r\n" +
				"s2->s2[label=c]\r\n" +
				"}";

		Automata test_automata1 = new Automata(file_content1);

		Automata test_automata2 = test_automata1.getComplement();

		String output_file = test_automata2.convertToDotty();

		File file1 = new File("./junit/junit_testComplement.gv");

		PrintWriter writer=null;

		try {
			file1.createNewFile();
			writer = new PrintWriter(file1.getAbsolutePath(), "UTF-8");
		} catch (FileNotFoundException e){
			e.printStackTrace();
		}catch(UnsupportedEncodingException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		writer.println(output_file);
		writer.close();


		assertEquals("Complement automata must equal the actual initial complement file content: ",output_file, result);
    }

    @Test
    public void testAcceptedSequences(){

		String file_content1 = "digraph{\n" +
				"\"\"[shape=none]\r\n" +
				"q1[shape=doublecircle]\r\n" +
				"\"\"->q0\r\n" +
				"q0->q0[label=a]\r\n" +
				"q0->q1[label=b]\r\n" +
				"q0->q0[label=c]\r\n" +
				"q1->q0[label=a]\r\n" +
				"q1->q1[label=b]\r\n" +
				"q1->q0[label=c]\r\n" +
				"}";

		Automata test_automata1 = new Automata(file_content1);

		String sequence1 = "bbbbbbbb";
		String sequence2 = "acac";
		String sequence3 = "bab";
		String sequence4 = "ccccccc";
		String sequence5 = "babcbabbbb";


		assertTrue("1 - Is sequence of caracters accepted by the automata: ", test_automata1.acceptsSequence(sequence1, test_automata1.getStartState()));
		assertFalse("2 - !(Is sequence of caracters accepted by the automata): ", test_automata1.acceptsSequence(sequence2, test_automata1.getStartState()));
		assertTrue("3 - Is sequence of caracters accepted by the automata: ", test_automata1.acceptsSequence(sequence3, test_automata1.getStartState()));
		assertFalse("4 - !(Is sequence of caracters accepted by the automata): ", test_automata1.acceptsSequence(sequence4, test_automata1.getStartState()));
		assertTrue("5 -  Is sequence of caracters accepted by the automata: ", test_automata1.acceptsSequence(sequence5, test_automata1.getStartState()));
    }
}
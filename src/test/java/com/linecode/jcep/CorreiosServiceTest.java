package com.linecode.jcep;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CorreiosServiceTest {

	private static final String EXPECTED_EXCEPTION_NOT_THROWN = "Expected exception not thrown";

	@Autowired
	private CorreiosService correiosService;

	@Test
	public void testConsultarCepAsyncWithNull() {
		try {
			correiosService.consultarCepAsync(null).get();
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(Exception exception) {
			assertNotNull(exception);
			assertTrue(exception.getMessage().contains(CorreiosService.EMPTY_CEP_VALUE_ERRO_MESSAGE));
		}
	}

	@Test
	public void testConsultarCepAsyncWithEmptyString() throws InterruptedException, ExecutionException {	
		try {
			correiosService.consultarCepAsync("").get();
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(Exception exception) {
			assertNotNull(exception);
			assertTrue(exception.getMessage().contains(CorreiosService.EMPTY_CEP_VALUE_ERRO_MESSAGE));
		}
	}

	@Test
	public void testConsultarCepAsyncWithInvalidCep() {
		try {
			correiosService.consultarCepAsync("ALAN12").get();
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(Exception exception) {
			assertNotNull(exception);
			assertTrue(exception.getMessage().contains(CorreiosService.INVALID_CEP_VALUE_ERRO_MESSAGE));
		}
	}

	@Test
	public void testConsultarCepAsyncWithSuccess() throws InterruptedException, ExecutionException {
		var endereco = correiosService.consultarCepAsync("49035620").get();
		assertNotNull(endereco);
		assertEquals("SE", endereco.getUf());
		assertEquals("Aracaju", endereco.getCidade());
		assertEquals("Coroa do Meio", endereco.getBairro());
		assertEquals("Avenida Desembargador José Antonio de Andrade Goes", endereco.getEnd());
	}

	@Test
	public void testConsultarCepWithNull() {
		try {
			correiosService.consultarCep(null);
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(IllegalArgumentException exception) {
			assertNotNull(exception);
			assertEquals(CorreiosService.EMPTY_CEP_VALUE_ERRO_MESSAGE, exception.getMessage());
		}
	}

	@Test
	public void testConsultarCepWithEmptyString() {
		try {
			correiosService.consultarCep("");
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(IllegalArgumentException exception) {
			assertNotNull(exception);
			assertEquals(CorreiosService.EMPTY_CEP_VALUE_ERRO_MESSAGE, exception.getMessage());
		}
	}

	@Test
	public void testConsultarCepWithInvalidCep() {
		try {
			correiosService.consultarCep("ALAN12");
			fail(EXPECTED_EXCEPTION_NOT_THROWN);
		} catch(IllegalArgumentException exception) {
			assertNotNull(exception);
			assertEquals(CorreiosService.INVALID_CEP_VALUE_ERRO_MESSAGE, exception.getMessage());
		}
	}

	@Test
	public void testConsultarCepWithSuccess() {
		var endereco = correiosService.consultarCep("49035620");
		assertNotNull(endereco);
		assertEquals("SE", endereco.getUf());
		assertEquals("Aracaju", endereco.getCidade());
		assertEquals("Coroa do Meio", endereco.getBairro());
		assertEquals("Avenida Desembargador José Antonio de Andrade Goes", endereco.getEnd());
	}
}

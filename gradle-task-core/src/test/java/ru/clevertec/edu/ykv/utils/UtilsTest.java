package ru.clevertec.edu.ykv.utils;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mockStatic;

class UtilsTest {
    private MockedStatic<StringUtils> mockStringUtils;

    @BeforeEach
    public void setUp() {
        mockStringUtils = mockStatic(StringUtils.class);
    }

    @AfterEach
    public void tearDown() {
        mockStringUtils.close();
    }

    public void init() {
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("12")).thenReturn(true);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("-12")).thenReturn(false);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("12")).thenReturn(true);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("-1.2")).thenReturn(false);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("1.2")).thenReturn(true);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("1,2")).thenReturn(false);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("74")).thenReturn(true);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber("hd23")).thenReturn(false);
        mockStringUtils.when(() -> StringUtils.isPositiveNumber(null)).thenReturn(false);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsAllPositiveNumber")
    @DisplayName("Should return true for positive number")
    void shouldReturnTrueForAllPositiveNumberMockito(String[] str, boolean result) {
        init();
        assertEquals(Utils.isAllPositiveNumbers(str), result);
    }

    @ParameterizedTest
    @MethodSource("provideStringsForIsAllPositiveNumber")
    @DisplayName("Should return true for positive number (without Mockito)")
    void shouldReturnTrueForPositiveNumber(String[] str, boolean result) {
        init();
        assertEquals(Utils.isAllPositiveNumbers2(str), result);
    }

    private static Stream<Arguments> provideStringsForIsAllPositiveNumber() {
        return Stream.of(
                Arguments.of(new String[]{"12","-12"}, false),
                Arguments.of(new String[]{"12","74"}, true),
                Arguments.of(new String[]{"-12","-1.2"}, false),
                Arguments.of(new String[]{"12","hd23"}, false),
                Arguments.of(null, false)
        );
    }
}
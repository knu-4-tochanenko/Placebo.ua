package ua.placebo.api.exception;

import java.util.Map;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ValidationErrors {
  private final Map<String, String> errors;
}

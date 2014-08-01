package com.batch.failure.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;

public class DoNothingWriter implements ItemWriter<Object> {

  @Override
  public void write(List<? extends Object> items) throws Exception {
    // TODO Auto-generated method stub

  }

}

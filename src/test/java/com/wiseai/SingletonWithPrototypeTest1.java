package com.wiseai;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Scope;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

public class SingletonWithPrototypeTest1 {
	@Test
	void singletonClientUserPrototype() {
		final AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(PrototypeBean.class, ClientBean.class);
		final ClientBean clientBean1 = ac.getBean(ClientBean.class);
		final int count1 = clientBean1.logic();
		assertThat(count1).isEqualTo(1);

		final ClientBean clientBean2 = ac.getBean(ClientBean.class);
		final int count2 = clientBean2.logic();
		assertThat(count2).isEqualTo(1);
		// final PrototypeBean prototypeBean = ac.getBean(PrototypeBean.class);
		// assertThat(clientBean2 == clientBean1).isEqualTo(true);
		// assertThat(clientBean2.getPrototypeBean() == clientBean1.getPrototypeBean()).isEqualTo(true);
		// assertThat(clientBean2.getPrototypeBean() != prototypeBean).isEqualTo(true);
		// ac.close();
	}

	@Scope("singleton")
	static class ClientBean {
		// private final PrototypeBean prototypeBean; // 생성 시점에 주입
		//
		// public ClientBean(final PrototypeBean prototypeBean) {
		// 	this.prototypeBean = prototypeBean;
		// }
		//
		// public int logic() {
		// 	prototypeBean.addCount();
		// 	return prototypeBean.getCount();
		// }
		// public PrototypeBean getPrototypeBean() {
		// 	return prototypeBean;
		// }
		private final ObjectProvider<PrototypeBean> prototypeBeanProvider;

		public ClientBean(final ObjectProvider<PrototypeBean> prototypeBeanProvider) {
			this.prototypeBeanProvider = prototypeBeanProvider;
		}

		public int logic() {
			final PrototypeBean prototypeBean = prototypeBeanProvider.getObject();
			prototypeBean.addCount();
			return prototypeBean.getCount();
		}
	}

	@Scope("prototype")
	static class PrototypeBean {
		private int count = 0;

		public void addCount() {
			count++;
		}

		public int getCount() {
			return count;
		}

		@PostConstruct
		public void init() {
			System.out.println("PrototypeBean.init " + this);
		}

		@PreDestroy
		public void destroy() {
			System.out.println("PrototypeBean.destroy");
		}
	}
}

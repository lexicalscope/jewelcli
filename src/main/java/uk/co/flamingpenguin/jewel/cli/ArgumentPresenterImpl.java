package uk.co.flamingpenguin.jewel.cli;

import static com.lexicalscope.fluentreflection.ReflectionMatchers.*;

import java.lang.reflect.Proxy;

import org.hamcrest.Matchers;

import com.lexicalscope.fluentreflection.dynamicproxy.FluentProxy;
import com.lexicalscope.fluentreflection.dynamicproxy.Implementing;
import com.lexicalscope.fluentreflection.dynamicproxy.MethodBody;

class ArgumentPresenterImpl<O> implements ArgumentPresenter<O> {
    private final OptionsSpecification<O> m_specification;
    private final Class<O> m_klass;

    public ArgumentPresenterImpl(final Class<O> klass, final OptionsSpecification<O> specification) {
        m_specification = specification;
        m_klass = klass;
    }

    /**
     * @inheritdoc
     */
    @SuppressWarnings("unchecked") public O presentArguments(final TypedArguments arguments) {
        final ArgumentInvocationHandler invokationHandler =
                new ArgumentInvocationHandler(m_klass, m_specification, arguments);
        final O result = (O) Proxy.newProxyInstance(m_klass.getClassLoader(),
                new Class[] { m_klass },
                invokationHandler);

        return FluentProxy.dynamicProxy(new Implementing<O>(m_klass) {
            private final Object identity = new Object();
            {
                matching(
                        callableHasName("hashCode").and(callableHasNoArguments()).and(callableHasReturnType(int.class)))
                        .execute(new MethodBody() {
                            @Override public void body() throws Throwable {
                                returnValue(identity.hashCode());
                            }
                        });

                matching(
                        callableHasName("equals")
                                .and(callableHasArguments(Object.class))
                                .and(callableHasReturnType(boolean.class)))
                        .execute(new MethodBody() {
                            @Override public void body() throws Throwable {
                                final Object that = args()[0];
                                returnValue(Proxy.isProxyClass(that.getClass())
                                        && that == proxy());
                            }
                        });

                matching(
                        callableHasName("toString").and(callableHasNoArguments()).and(
                                callableHasReturnType(String.class))).execute(new MethodBody() {
                    @Override public void body() throws Throwable {
                        returnValue(invokationHandler.toString());
                    }
                });

                matching(Matchers.anything()).execute(new MethodBody() {
                    public void body() throws Throwable {
                        returnValue(invokationHandler.invoke(null, method().methodUnderReflection(), args()));
                    }
                });
            }
        });
    }
}

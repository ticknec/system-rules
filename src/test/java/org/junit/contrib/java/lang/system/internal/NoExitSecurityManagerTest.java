package org.junit.contrib.java.lang.system.internal;

import static com.github.stefanbirkner.fishbowl.Fishbowl.exceptionThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileDescriptor;
import java.net.InetAddress;
import java.security.Permission;

import com.github.stefanbirkner.fishbowl.Statement;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

public class NoExitSecurityManagerTest {
	private static final int DUMMY_STATUS = 1;

	@Rule
	public final TemporaryFolder temporaryFolder = new TemporaryFolder();

	private final SecurityManager originalSecurityManager = mock(SecurityManager.class);
	private final NoExitSecurityManager managerWithOriginal = new NoExitSecurityManager(
		originalSecurityManager);
	private final NoExitSecurityManager managerWithoutOriginal = new NoExitSecurityManager(null);

	@Test
	public void throwExceptionWhenCheckExitIsCalled() {
		CheckExitCalled exception = exceptionThrownBy(new Statement() {
			public void evaluate() throws Throwable {
				managerWithOriginal.checkExit(DUMMY_STATUS);
			}
		}, CheckExitCalled.class);
		assertThat(exception.getStatus()).isEqualTo(DUMMY_STATUS);
	}

	@Test
	public void delegateGetInCheck() {
		when(originalSecurityManager.getInCheck()).thenReturn(true);
		assertThat(managerWithOriginal.getInCheck()).isTrue();
	}

	@Test
	public void isNeverInCheckWithoutParent() {
		assertThat(managerWithoutOriginal.getInCheck()).isFalse();
	}

	@Test
	public void provideSecurityContextOfOriginalSecurityManager() {
		Object context = new Object();
		when(originalSecurityManager.getSecurityContext()).thenReturn(context);
		assertThat(managerWithOriginal.getSecurityContext()).isSameAs(context);
	}

	@Test
	public void dontFailWithoutParentForGetSecurityContext() {
		managerWithoutOriginal.getSecurityContext();
	}

	@Test
	public void delegateCheckPermission_Permission() {
		Permission permission = mock(Permission.class);
		managerWithOriginal.checkPermission(permission);
		verify(originalSecurityManager).checkPermission(permission);
	}

	@Test
	public void dontFailWithoutParentForCheckPermission_Permission() {
		Permission permission = mock(Permission.class);
		managerWithoutOriginal.checkPermission(permission);
	}

	@Test
	public void delegateCheckPermission_Permission_Object() {
		Permission permission = mock(Permission.class);
		Object context = new Object();
		managerWithOriginal.checkPermission(permission, context);
		verify(originalSecurityManager).checkPermission(permission, context);
	}

	@Test
	public void dontFailWithoutParentForCheckPermission_Permission_Object() {
		Permission permission = mock(Permission.class);
		Object context = new Object();
		managerWithoutOriginal.checkPermission(permission, context);
	}

	@Test
	public void delegateCheckCreateClassLoader() {
		managerWithOriginal.checkCreateClassLoader();
		verify(originalSecurityManager).checkCreateClassLoader();
	}

	@Test
	public void dontFailWithoutParentForCheckCreateClassLoader() {
		managerWithoutOriginal.checkCreateClassLoader();
	}

	@Test
	public void delegateCheckAccess_Thread() {
		Thread thread = mock(Thread.class);
		managerWithOriginal.checkAccess(thread);
		verify(originalSecurityManager).checkAccess(thread);
	}

	@Test
	public void dontFailWithoutParentForCheckAccess_Thread() {
		Thread thread = mock(Thread.class);
		managerWithoutOriginal.checkAccess(thread);
	}

	@Test
	public void delegateCheckAccess_ThreadGroup() {
		ThreadGroup threadGroup = mock(ThreadGroup.class);
		managerWithOriginal.checkAccess(threadGroup);
		verify(originalSecurityManager).checkAccess(threadGroup);
	}

	@Test
	public void dontFailWithoutParentForCheckAccess_ThreadGroup() {
		ThreadGroup threadGroup = mock(ThreadGroup.class);
		managerWithoutOriginal.checkAccess(threadGroup);
	}

	@Test
	public void delegateCheckExec() {
		managerWithOriginal.checkExec("arbitrary cmd");
		verify(originalSecurityManager).checkExec("arbitrary cmd");
	}

	@Test
	public void dontFailWithoutParentForCheckExec() {
		managerWithoutOriginal.checkExec("dummy cmd");
	}

	@Test
	public void delegateCheckLink() {
		managerWithOriginal.checkLink("arbitrary lib");
		verify(originalSecurityManager).checkLink("arbitrary lib");
	}

	@Test
	public void dontFailWithoutParentForCheckLink() {
		managerWithoutOriginal.checkLink("dummy lib");
	}

	@Test
	public void delegateCheckRead_FileDescriptor() {
		FileDescriptor fileDescriptor = new FileDescriptor();
		managerWithOriginal.checkRead(fileDescriptor);
		verify(originalSecurityManager).checkRead(fileDescriptor);
	}

	@Test
	public void dontFailWithoutParentForCheckRead_FileDescriptor() {
		FileDescriptor fileDescriptor = new FileDescriptor();
		managerWithoutOriginal.checkRead(fileDescriptor);
	}

	@Test
	public void delegateCheckRead_String() {
		managerWithOriginal.checkRead("arbitrary file");
		verify(originalSecurityManager).checkRead("arbitrary file");
	}

	@Test
	public void dontFailWithoutParentForCheckRead_String() {
		managerWithoutOriginal.checkRead("dummy file");
	}

	@Test
	public void delegateCheckRead_String_Context() {
		Object context = new Object();
		managerWithOriginal.checkRead("arbitrary file", context);
		verify(originalSecurityManager).checkRead("arbitrary file", context);
	}

	@Test
	public void dontFailWithoutParentForCheckRead_String_Context() {
		Object context = new Object();
		managerWithoutOriginal.checkRead("dummy file", context);
	}

	@Test
	public void delegateCheckWrite_FileDescriptor() {
		FileDescriptor fileDescriptor = new FileDescriptor();
		managerWithOriginal.checkWrite(fileDescriptor);
		verify(originalSecurityManager).checkWrite(fileDescriptor);
	}

	@Test
	public void dontFailWithoutParentForCheckWrite_FileDescriptor() {
		FileDescriptor fileDescriptor = new FileDescriptor();
		managerWithoutOriginal.checkWrite(fileDescriptor);
	}

	@Test
	public void delegateCheckWrite_String() {
		managerWithOriginal.checkWrite("arbitrary file");
		verify(originalSecurityManager).checkWrite("arbitrary file");
	}

	@Test
	public void dontFailWithoutParentForCheckWrite_String() {
		managerWithoutOriginal.checkWrite("dummy file");
	}

	@Test
	public void delegateCheckDelete() {
		managerWithOriginal.checkDelete("arbitrary file");
		verify(originalSecurityManager).checkDelete("arbitrary file");
	}

	@Test
	public void dontFailWithoutParentForCheckDelete() {
		managerWithoutOriginal.checkDelete("dummy file");
	}

	@Test
	public void delegateCheckConnect() {
		int port = 234;
		managerWithOriginal.checkConnect("host", port);
		verify(originalSecurityManager).checkConnect("host", port);
	}

	@Test
	public void dontFailWithoutParentForCheckConnect() {
		int port = 234;
		managerWithoutOriginal.checkConnect("host", port);
	}

	@Test
	public void delegateCheckConnectWithContext() {
		int port = 234;
		Object context = new Object();
		managerWithOriginal.checkConnect("host", port, context);
		verify(originalSecurityManager).checkConnect("host", port, context);
	}

	@Test
	public void dontFailWithoutParentForCheckConnectWithContext() {
		int port = 234;
		Object context = new Object();
		managerWithoutOriginal.checkConnect("host", port, context);
	}

	@Test
	public void delegateCheckListen() {
		int port = 234;
		managerWithOriginal.checkListen(port);
		verify(originalSecurityManager).checkListen(port);
	}

	@Test
	public void dontFailWithoutParentForCheckListen() {
		int port = 234;
		managerWithoutOriginal.checkListen(port);
	}

	@Test
	public void delegateCheckAccept() {
		int port = 234;
		managerWithOriginal.checkAccept("host", port);
		verify(originalSecurityManager).checkAccept("host", port);
	}

	@Test
	public void dontFailWithoutParentForCheckAccept() {
		int port = 234;
		managerWithoutOriginal.checkAccept("host", port);
	}

	@Test
	public void delegateCheckMulticast() {
		InetAddress inetAddress = mock(InetAddress.class);
		managerWithOriginal.checkMulticast(inetAddress);
		verify(originalSecurityManager).checkMulticast(inetAddress);
	}

	@Test
	public void dontFailWithoutParentForCheckMulticast() {
		InetAddress inetAddress = mock(InetAddress.class);
		managerWithoutOriginal.checkMulticast(inetAddress);
	}

	@Test
	public void delegateCheckMulticastWithTtl() {
		InetAddress inetAddress = mock(InetAddress.class);
		byte ttl = 24;
		managerWithOriginal.checkMulticast(inetAddress, ttl);
		verify(originalSecurityManager).checkMulticast(inetAddress, ttl);
	}

	@Test
	public void dontFailWithoutParentForCheckMulticastWithTtl() {
		InetAddress inetAddress = mock(InetAddress.class);
		byte ttl = 24;
		managerWithoutOriginal.checkMulticast(inetAddress, ttl);
	}

	@Test
	public void delegateCheckPropertiesAccess() {
		managerWithOriginal.checkPropertiesAccess();
		verify(originalSecurityManager).checkPropertiesAccess();
	}

	@Test
	public void dontFailWithoutParentForCheckPropertiesAccess() {
		managerWithoutOriginal.checkPropertiesAccess();
	}

	@Test
	public void delegateCheckPropertyAccess() {
		managerWithOriginal.checkPropertyAccess("arbitrary key");
		verify(originalSecurityManager).checkPropertyAccess("arbitrary key");
	}

	@Test
	public void dontFailWithoutParentForCheckPropertyAccess() {
		managerWithoutOriginal.checkPropertyAccess("dummy key");
	}

	@Test
	public void delegateCheckTopLevelWindow() {
		Object window = new Object();
		when(originalSecurityManager.checkTopLevelWindow(window)).thenReturn(true);
		assertThat(managerWithOriginal.checkTopLevelWindow(window)).isTrue();
	}

	@Test
	public void dontFailWithoutParentForCheckTopLevelWindow() {
		Object window = new Object();
		managerWithoutOriginal.checkTopLevelWindow(window);
	}

	@Test
	public void delegateCheckPrintJobAccess() {
		managerWithOriginal.checkPrintJobAccess();
		verify(originalSecurityManager).checkPrintJobAccess();
	}

	@Test
	public void dontFailWithoutParentForCheckPrintJobAccess() {
		managerWithoutOriginal.checkPrintJobAccess();
	}

	@Test
	public void delegateCheckSystemClipboardAccess() {
		managerWithOriginal.checkSystemClipboardAccess();
		verify(originalSecurityManager).checkSystemClipboardAccess();
	}

	@Test
	public void dontFailWithoutParentForCheckSystemClipboardAccess() {
		managerWithoutOriginal.checkSystemClipboardAccess();
	}

	@Test
	public void delegateCheckAwtEventQueueAccess() {
		managerWithOriginal.checkAwtEventQueueAccess();
		verify(originalSecurityManager).checkAwtEventQueueAccess();
	}

	@Test
	public void dontFailWithoutParentForCheckAwtEventQueueAccess() {
		managerWithoutOriginal.checkAwtEventQueueAccess();
	}

	@Test
	public void delegateCheckPackageAccess() {
		managerWithOriginal.checkPackageAccess("arbitrary package");
		verify(originalSecurityManager).checkPackageAccess("arbitrary package");
	}

	@Test
	public void dontFailWithoutParentForCheckPackageAccess() {
		managerWithoutOriginal.checkPackageAccess("dummy package");
	}

	@Test
	public void delegateCheckPackageDefinition() {
		managerWithOriginal.checkPackageDefinition("arbitrary package");
		verify(originalSecurityManager).checkPackageDefinition("arbitrary package");
	}

	@Test
	public void dontFailWithoutParentForCheckPackageDefinition() {
		managerWithoutOriginal.checkPackageDefinition("dummy package");
	}

	@Test
	public void delegateCheckSetFactory() {
		managerWithOriginal.checkSetFactory();
		verify(originalSecurityManager).checkSetFactory();
	}

	@Test
	public void dontFailWithoutParentForCheckSetFactory() {
		managerWithoutOriginal.checkSetFactory();
	}

	@Test
	public void delegateCheckMemberAccess() {
		Class<?> arbitraryClass = Integer.class;
		int which = 394;
		managerWithOriginal.checkMemberAccess(arbitraryClass, which);
		verify(originalSecurityManager).checkMemberAccess(arbitraryClass, which);
	}

	@Test
	public void dontFailWithoutParentForCheckMemberAccess() {
		Class<?> arbitraryClass = Integer.class;
		int which = 394;
		managerWithoutOriginal.checkMemberAccess(arbitraryClass, which);
	}

	@Test
	public void delegateCheckSecurityAccess() {
		managerWithOriginal.checkSecurityAccess("arbitrary target");
		verify(originalSecurityManager).checkSecurityAccess("arbitrary target");
	}

	@Test
	public void dontFailWithoutParentForCheckSecurityAccess() {
		managerWithoutOriginal.checkSecurityAccess("arbitrary target");
	}

	@Test
	public void provideThreadGroupOfOriginalSecurityManager() {
		ThreadGroup threadGroup = new ThreadGroup("dummy name");
		when(originalSecurityManager.getThreadGroup()).thenReturn(threadGroup);
		assertThat(managerWithOriginal.getThreadGroup()).isSameAs(threadGroup);
	}

	@Test
	public void dontFailWithoutParentForGetThreadGroup() {
		managerWithoutOriginal.getThreadGroup();
	}

	@Test
	public void provideInformationThatCheckExitHasNotBeenCalled() {
		assertThat(managerWithOriginal.isCheckExitCalled()).isFalse();
	}

	@Test
	public void provideInformationThatCheckExitHasBeenCalled() {
		safeCallCheckExitWithStatus(DUMMY_STATUS);
		assertThat(managerWithOriginal.isCheckExitCalled()).isTrue();
	}

	@Test
	public void providesStatusOfFirstCheckExitCall() {
		safeCallCheckExitWithStatus(DUMMY_STATUS);
		safeCallCheckExitWithStatus(DUMMY_STATUS + 1);
		assertThat(managerWithOriginal.getStatusOfFirstCheckExitCall()).isEqualTo(DUMMY_STATUS);
	}

	private void safeCallCheckExitWithStatus(int status) {
		try {
			managerWithOriginal.checkExit(status);
		} catch (CheckExitCalled ignored) {
		}
	}

	@Test
	public void doesNotProvideStatusOfFirstCheckExitCallWithoutCall() {
		Throwable exception = exceptionThrownBy(new Statement() {
			public void evaluate() throws Throwable {
				managerWithOriginal.getStatusOfFirstCheckExitCall();
			}
		});
		assertThat(exception)
			.isInstanceOf(IllegalStateException.class)
			.hasMessage("checkExit(int) has not been called.");
	}
}

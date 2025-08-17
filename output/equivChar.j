.class public equivChar
.super java/lang/Object

.method public <init>()V
    aload_0
    invokenonvirtual java/lang/Object/<init>()V
    return
.end method

.method public static main([Ljava/lang/String;)V
    .limit stack 10
    .limit locals 10
    invokestatic equivChar/_main()V
    return
.end method

.method public static _main()V
    .limit stack 10
    .limit locals 10
    ldc 65
    ldc 65
    if_icmpeq L0
    iconst_0
    goto L1
L0:
    iconst_1
L1:
    istore 0
    ldc 65
    ldc 65
    if_icmpeq L2
    iconst_0
    goto L3
L2:
    iconst_1
L3:
    istore 1
    ldc 66
    ldc 65
    if_icmpeq L4
    iconst_0
    goto L5
L4:
    iconst_1
L5:
    istore 2
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 0
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 1
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    iload 2
    invokevirtual java/io/PrintStream/print(Z)V
    getstatic java/lang/System/out Ljava/io/PrintStream;
    ldc 10
    invokevirtual java/io/PrintStream/print(C)V
    return
.end method



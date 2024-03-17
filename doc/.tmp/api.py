import os
import glob
import re


def find_functions(path):
    files = glob.glob(path + "/**/*Controller.java", recursive=True)
    function_names = []
    for file in files:
        with open(file, "r", encoding="utf-8") as f:
            content = f.read()
            # matches = re.findall(r"public\s+(\w+)\(", content)
            # matches = re.findall(r"public\s+\w+\s+(\w+)\(", content)
            matches = re.findall(r"public\s+[\w<>,\s]+\s+(\w+)\(", content)
            for match in matches:
                function_names.append(
                    os.path.basename(file).replace(".java", "") + "_" + match + ".png"
                )
    return function_names


# 使用函数
path = "doctorarrival"  # 替换为你的路径
functions = find_functions(path)
for function in functions:
    print(function)

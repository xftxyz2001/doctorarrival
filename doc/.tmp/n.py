import re

def add_number_to_subtitles(file_path):
    with open(file_path, 'r', encoding='utf-8') as file:
        lines = file.readlines()

    section_counter = 1
    new_lines = []
    current_section = None
    for line in lines:
        if line.startswith('## '):
            current_section = re.match(r'## (\d+\.\d+) (.*)', line).group(1)
            section_counter = 1
        elif line.startswith('### ') and current_section is not None:
            line = re.sub(r'### (.*)', r'### ' + current_section + '.' + str(section_counter) + r' \1', line)
            section_counter += 1
        new_lines.append(line)

    with open(file_path, 'w', encoding='utf-8') as file:
        file.writelines(new_lines)

add_number_to_subtitles('第四章 实现.md')